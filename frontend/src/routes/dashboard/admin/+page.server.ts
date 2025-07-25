import type { Actions, PageServerLoad } from './$types';
import { createUser, deleteUser, editUser, getAllUsers, verifyUser } from '$lib/api/users';
import { fail } from '@sveltejs/kit';
import type { Role, User } from '$lib/types';
import { getAllLogs } from '$lib/api/medicChangeLog';
import { perEnvironmentPlugin } from 'vite';

export const load: PageServerLoad = ({ locals, cookies }) => {
  let token = cookies.get('token');

  let userList = getAllUsers(token);
  let changelog = getAllLogs(token);

  return {
    user: locals.user,
    userList,
    changelog, 
  };
};

export const actions: Actions = {
  createUser: async ({ request, cookies }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for editing report');
      return fail(403, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    const user: NestedRequiredBy<User, 'email' | 'password' | 'role'> = {
      email: form.get('email') as string,
      password: form.get('password') as string,
      role: form.get('role') as Role
    };

    if (user.role == 'PATIENT') {
      user.patient = {
        firstName: form.get('patientFirstName') as string,
        lastName: form.get('patientLastName') as string,
        birthDate: form.get('birthDate') as string
      };
    } else if (user.role == 'MEDIC') {
      user.medic = {
        firstName: form.get('medicFirstName') as string,
        lastName: form.get('medicLastName') as string,
        email: user.email
      };
    }

    const createRes = await createUser(token, user);

    if (!createRes.ok) {
      return fail(createRes.status, { error: await createRes.text() });
    }

    let createdUser: User = await createRes.json();

    const verifyRes = await verifyUser(token, createdUser.id);
    if (!verifyRes.ok) {
      console.error('Failed to verify user:');
      return fail(verifyRes.status, { error: await verifyRes.text() });
    }
  },

  editUser: async ({ request, cookies }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for editing report');
      return fail(403, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    const userId = parseInt(form.get('userId') as string);

    if (isNaN(userId)) {
      return fail(400, { error: 'Invalid user ID' });
    }

    const user: NestedRequiredBy<User, 'email' | 'password' | 'role'> = {
      email: form.get('email') as string,
      password: form.get('password') as string,
      role: form.get('role') as Role,
      verified: form.get('verified') === 'true'
    };

    if (user.role == 'PATIENT') {
      user.patient = {
        firstName: form.get('patientFirstName') as string,
        lastName: form.get('patientLastName') as string,
        birthDate: form.get('birthDate') as string
      };
    } else if (user.role == 'MEDIC') {
      user.medic = {
        firstName: form.get('medicFirstName') as string,
        lastName: form.get('medicLastName') as string,
        email: user.email
      };
    }

    const res = await editUser(userId, user, token);

    if (!res.ok) {
      return fail(res.status, { error: await res.text() });
    }
  },

  deleteUser: async ({ request, cookies }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for deleting user');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    const userId = parseInt(form.get('userId') as string);

    if (isNaN(userId)) {
      return fail(400, { error: 'Invalid form data' });
    }

    const res = await deleteUser(userId, token);

    if (res == null) {
      console.error('Failed to delete user');
      return fail(500, { error: 'Failed to delete user' });
    }
  }
};
