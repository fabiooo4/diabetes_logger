import { authenticateUser } from '$lib/api/auth';
import { redirect, type Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
  // Don't allow unauthenticated access to the dashboard
  if (event.url.pathname.startsWith('/dashboard')) {
    event.locals.user = await authenticateUser(event);

    if (!event.locals.user) {
      console.error('User is not authenticated');
      throw redirect(302, '/login');
    }

    if (event.url.pathname.startsWith('/dashboard/admin')) {
      if (event.locals.user.role !== 'ADMIN') {
        throw redirect(302, '/login');
      }
    }

    if (event.url.pathname.startsWith('/dashboard/patient')) {
      if (event.locals.user.role !== 'PATIENT') {
        throw redirect(302, '/login');
      }
    }

    if (event.url.pathname.startsWith('/dashboard/medic')) {
      if (event.locals.user.role !== 'MEDIC') {
        throw redirect(302, '/login');
      }
    }
  }

  const response = await resolve(event);
  return response;
};
