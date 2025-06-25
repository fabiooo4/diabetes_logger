// +page.server.ts
import { redirect, fail, type Actions } from '@sveltejs/kit';
import { login } from '$lib/api/auth';

export const actions: Actions = {
  login: async ({ request, cookies }) => {
    const form = await request.formData();
    const email = form.get('email');
    const password = form.get('password');

    if (typeof email !== 'string' || typeof password !== 'string') {
      return fail(400, { invalid: true });
    }

    const res = await login(email, password);
    if (!res.ok) {
      return fail(res.status, { loginError: true });
    }

    const { token, userId }: { token: string; userId: number } = await res.json();

    cookies.set('token', token, {
      path: '/',
      httpOnly: true,
      sameSite: 'strict',
      maxAge: 60 * 60 * 60 * 60 * 2
    });

    cookies.set('userId', userId.toString(), {
      path: '/',
      httpOnly: true,
      sameSite: 'strict',
      maxAge: 60 * 60 * 60 * 60 * 2
    });

    throw redirect(303, '/');
  }
};
