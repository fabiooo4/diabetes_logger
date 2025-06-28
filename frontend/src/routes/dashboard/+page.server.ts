import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from '../$types';
import type { User } from '$lib/types';

export const load: PageServerLoad = ({ locals }) => {
	// Redirect the user to its dashboard based on its role
	let user: User | null = locals.user;

	if (user == null) {
    console.error('User is not authenticated');
		throw redirect(302, '/login');
	}

	switch (user.role) {
    case 'PATIENT':
      throw redirect(302, '/dashboard/patient');
    case 'MEDIC':
      throw redirect(302, '/dashboard/medic');
		case 'ADMIN':
			throw redirect(302, '/dashboard/admin');
		default:
			break;
	}

	throw redirect(302, '/');
};
