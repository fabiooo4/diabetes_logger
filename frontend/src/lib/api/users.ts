import { PUBLIC_API_BASE } from '$env/static/public';
import type { User } from '$lib/types';

export async function getAllUsers(token: string | undefined): Promise<User[]> {
  return fetch(PUBLIC_API_BASE + '/users', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to fetch users: ' + response.statusText);
      }

      return response.json();
    })
    .then((data: User[]) => {
      return data;
    })
    .catch((error) => {
      console.error('Error fetching users:', error);
      return [];
    });
}
