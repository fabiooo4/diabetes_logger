import { PUBLIC_API_BASE } from '$env/static/public';
import type { Notification } from '$lib/types';

export async function getAllUserNotifications(
  token: string | undefined,
  userId: number | undefined
): Promise<Notification[]> {
  if (!token) {
    console.error('No token provided for creating notification');
    return [];
  }

  if (userId == undefined) {
    console.error('No userId provided for fetching notifications');
    return [];
  }

  return fetch(PUBLIC_API_BASE + '/notifications/user/' + userId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to fetch notifications: ' + response.statusText);
      }

      return response.json();
    })
    .then((data: Notification[]) => {
      return data;
    })
    .catch((error) => {
      console.error('Error fetching notifications:', error);
      return [];
    });
}
