import { redirect, fail, type Actions } from '@sveltejs/kit';
import { register } from '$lib/api/auth';
import type { Role, User } from '$lib/types';

export const actions: Actions = {
  register: async ({ request }) => {
    const form = await request.formData();
    const user: NestedRequiredBy<User, 'email' | 'password' | 'role'> = {
      email: form.get('email') as string,
      password: form.get('password') as string,
      role: form.get('role') as Role
    };


    if (!user.email || !user.password || (user.role !== 'PATIENT' && user.role !== 'MEDIC')) {
      console.error('Invalid registration data:', {
        email: user.email,
        password: user.password,
        role: user.role
      });

      return fail(400, {
        registerError: true,
        message: 'Email, password, and role fields are required.'
      });
    }

    if (user.role == 'PATIENT') {
      user.patient = {
        firstName: form.get('patientFirstName') as string,
        lastName: form.get('patientLastName') as string,
        birthDate: form.get('birthDate') as string
      };

      if (!user.patient.firstName || !user.patient.lastName || !user.patient.birthDate) {
        console.error('Invalid patient data:', {
          firstName: user.patient.firstName,
          lastName: user.patient.lastName,
          birthDate: user.patient.birthDate
        });

        return fail(400, {
          registerError: true,
          message: 'First name, last name, and birth date are required for patients.'
        });
      }
    } else if (user.role == 'MEDIC') {
      user.medic = {
        firstName: form.get('medicFirstName') as string,
        lastName: form.get('medicLastName') as string
      };

      if (!user.medic.firstName || !user.medic.lastName) {
        console.error('Invalid medic data:', {
          firstName: user.medic.firstName,
          lastName: user.medic.lastName
        });

        return fail(400, {
          registerError: true,
          message: 'First name and last name are required for medics.'
        });
      }
    } else {
      console.error('Invalid role:', user.role);
      return fail(400, { registerError: true, message: 'Invalid role.' });
    }

    console.log(user)

    const res = await register(user);
    if (!res.ok) {
      console.error('Registration failed:', res.status, await res.text());
      return fail(res.status, { registerError: true });
    }

    console.log('User registered successfully');

    throw redirect(302, '/login');
  }
};
