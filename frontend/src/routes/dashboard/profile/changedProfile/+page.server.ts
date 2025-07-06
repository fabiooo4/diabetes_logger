import type { PageServerLoad } from './$types';

export const load: PageServerLoad = ({ locals, cookies }) => {
	console.log('Email or password changed, re-logging in');
	cookies.delete('token', { path: '/' });
	cookies.delete('userId', { path: '/' });

	locals.user = null;

	return {};
};
