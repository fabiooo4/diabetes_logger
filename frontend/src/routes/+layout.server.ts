import { getAllUserNotifications } from '$lib/api/notifications';
import type { Cookies } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = ({ locals, cookies }: { locals: App.Locals, cookies: Cookies }) => {
  let token = cookies.get('token');

  let notifications = getAllUserNotifications(token, locals.user?.id);

  return {
    user: locals.user,
    notifications: notifications,
  };
};
