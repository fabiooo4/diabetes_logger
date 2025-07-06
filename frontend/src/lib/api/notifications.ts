import { PUBLIC_API_BASE } from '$env/static/public';
import type { Notification } from '$lib/types';

export async function readAllUserNotifications(
	token: string | undefined,
	userId: number | undefined
): Promise<Notification[]> {
	if (!token) {
		console.error('No token provided for reading notification');
		return [];
	}

	if (userId == undefined) {
		console.error('No userId provided for reading notifications');
		return [];
	}

	return fetch(PUBLIC_API_BASE + '/notifications/user/' + userId.toString(), {
		method: 'PATCH',
		headers: {
			'Content-Type': 'application/json',
			Authorization: 'Bearer ' + token
		}
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error('Failed to read notifications: ' + response.statusText);
			}

			return response.json();
		})
		.then((data: Notification[]) => {
			return data;
		})
		.catch((error) => {
			console.error('Error reading notifications:', error);
			return [];
		});
}

export async function getAllUserNotifications(
	token: string | undefined,
	userId: number | undefined
): Promise<Notification[]> {
	if (!token) {
		console.error('No token provided for fetching notification');
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

export async function deleteNotification(
	token: string | undefined,
	userId: number | undefined,
	notificationId: number | undefined
): Promise<Notification | null> {
	if (!token) {
		console.error('No token provided for deleting notification');
		return null;
	}

	if (userId == undefined) {
		console.error('No userId provided for deleting notifications');
		return null;
	}

	if (notificationId == undefined) {
		console.error('No notificationId provided for deleting notifications');
		return null;
	}

	return fetch(PUBLIC_API_BASE + '/notifications/user/' + userId + '/' + notificationId, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
			Authorization: 'Bearer ' + token
		}
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error('Failed to delete notification: ' + response.statusText);
			}

			return response.json();
		})
		.then((data: Notification) => {
			return data;
		})
		.catch((error) => {
			console.error('Error deleting notification:', error);
			return null;
		});
}
