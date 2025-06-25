import { PUBLIC_API_BASE } from '$env/static/public';

export async function login(email: string, password: string): Promise<void> {
  fetch(PUBLIC_API_BASE + '/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, password })
  })
    .then((response) => {
      // Error handling
      if (!response.ok) {
        throw new Error('Login failed');
      }
      return response.text();
    })
    .then((jwt_token: string) => {
      // JWT token storage
      localStorage.setItem('jwt_token', jwt_token);
    });
}
