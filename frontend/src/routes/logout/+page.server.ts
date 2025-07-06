import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from '../$types';

export const load: PageServerLoad = ({ cookies }) => {
	cookies.delete('token', { path: '/' });
	cookies.delete('userId', { path: '/' });

	throw redirect(302, '/');
};
