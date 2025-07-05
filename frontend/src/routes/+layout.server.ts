import { getAllUserNotifications } from '$lib/api/notifications';
import type { Cookies } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import type { Notification } from '$lib/types';

export const load: PageServerLoad = ({
  locals,
  cookies
}: {
  locals: App.Locals;
  cookies: Cookies;
}) => {
  let token = cookies.get('token');

  let notifications: Promise<Notification[]>;
  if (locals.user?.id && token) {
    notifications = getAllUserNotifications(token, locals.user?.id);
  } else {
    notifications = Promise.resolve([]);
  }

  return {
    user: locals.user,
    token: token,
    notifications: notifications
  };
};
