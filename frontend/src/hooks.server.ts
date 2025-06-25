import { authenticateUser } from '$lib/api/auth';
import { redirect, type Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
  // Don't allow unauthenticated access to the dashboard
  if (event.url.pathname.startsWith('/dashboard')) {
    event.locals.user = await authenticateUser(event);

    if (!event.locals.user) {
      throw redirect(302, '/login');
    }
  }

  const response = await resolve(event);
  return response;
};
