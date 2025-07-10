import { updateUser } from '$lib/api/users';
import type { Role, User } from '$lib/types';
import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';

export const load: PageServerLoad = ({ locals }) => {
  return {
    user: locals.user
  };
};

export const actions: Actions = {
  editProfile: async ({ request, locals, cookies }) => {
    const token = cookies.get('token');
    if (!token) {
      console.error('No token found');
      throw new Error('No token found');
    }

    const form = await request.formData();
    let email: string | undefined = form.get('email') as string;
    email = email ? email : undefined;

    const password =
      (form.get('password') as string) === '' ? undefined : (form.get('password') as string);
    const role = form.get('role') as Role;

    let firstName: string | undefined = form.get('firstName') as string;
    firstName = firstName ? firstName : undefined;
    let lastName: string | undefined = form.get('lastName') as string;
    lastName = lastName ? lastName : undefined;
    let birthDate: string | undefined = form.get('birthDate') as string;
    birthDate = birthDate ? birthDate : undefined;

    let user: NestedPartial<User>;
    if (role === 'PATIENT') {
      user = {
        email,
        password,
        role: locals.user?.role,
        patient: {
          firstName,
          lastName,
          birthDate
        }
      };
    } else if (role === 'MEDIC') {
      user = {
        email,
        password,
        role: locals.user?.role,
        medic: {
          firstName,
          lastName
        }
      };
    } else {
      user = {
        email,
        password,
        role: locals.user?.role
      };
    }

    const res = await updateUser(locals.user?.id, user, token);

    if (!res.ok) {
      return fail(res.status, { error: await res.text() });
    }

    res.json().then((updatedUser: User) => {
      locals.user = updatedUser;
    });

    // If email or password has changed, the user needs to log in again
    if ((locals.user?.email !== email && email != undefined) || password !== undefined) {
      redirect(303, '/dashboard/profile/changedProfile');
    } else {
      // Refresh page
      redirect(303, '/dashboard/profile');
    }
  }
};
