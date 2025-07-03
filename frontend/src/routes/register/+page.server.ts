// +page.server.ts
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
      return fail(400, {
        registerError: true,
        message: 'Email, password, and role fields are required.'
      });
    }

    if (user.role == 'PATIENT') {
      user.patient = {
        firstName: form.get('firstName') as string,
        lastName: form.get('lastName') as string,
        birthDate: form.get('birthDate') as string
      };

      if (!user.patient.firstName || !user.patient.lastName || !user.patient.birthDate) {
        return fail(400, {
          registerError: true,
          message: 'First name, last name, and birth date are required for patients.'
        });
      }
    } else if (user.role == 'MEDIC') {
      user.medic = {
        firstName: form.get('firstName') as string,
        lastName: form.get('lastName') as string
      };

      if (!user.medic.firstName || !user.medic.lastName) {
        return fail(400, {
          registerError: true,
          message: 'First name and last name are required for medics.'
        });
      }
    } else {
      return fail(400, { registerError: true, message: 'Invalid role.' });
    }

    const res = await register(user);
    if (!res.ok) {
      return fail(res.status, { registerError: true });
    }

    console.log('User registered successfully');

    throw redirect(302, '/login');
  }
};
