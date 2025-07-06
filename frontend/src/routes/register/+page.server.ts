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

		if (user.role == 'PATIENT') {
			user.patient = {
				firstName: form.get('patientFirstName') as string,
				lastName: form.get('patientLastName') as string,
				birthDate: form.get('birthDate') as string
			};
		} else if (user.role == 'MEDIC') {
			user.medic = {
				firstName: form.get('medicFirstName') as string,
				lastName: form.get('medicLastName') as string
			};
		}

		const res = await register(user);
		if (!res.ok) {
			return fail(res.status, { error: await res.text() });
		}

		throw redirect(302, '/login');
	}
};
