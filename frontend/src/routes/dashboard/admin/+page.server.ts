import type { PageServerLoad } from './$types';
	import { getAllUsers } from '$lib/api/users';

export const load: PageServerLoad = ({ locals, cookies }) => {
  let token = cookies.get("token");

	let userList = getAllUsers(token);

	return {
		user: locals.user,
    userList: userList
	};
};
