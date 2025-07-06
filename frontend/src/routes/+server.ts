import { deleteNotification, readAllUserNotifications } from '$lib/api/notifications';
import { json, type Cookies } from '@sveltejs/kit';

export async function PATCH({ request, cookies }: { request: Request; cookies: Cookies }) {
	let token = cookies.get('token');
	let userId: number = await request.json().then((data) => data.userId);

	if (token == undefined || token == '' || token == null) {
		console.error('No token provided for reading notification');
		return json({ error: 'No token provided' }, { status: 400 });
	}
	if (userId == undefined) {
		console.error('No userId provided for reading notifications');
		return json({ error: 'No userId provided' }, { status: 400 });
	}

	let readNotifications = await readAllUserNotifications(token, userId);

	return json(readNotifications, { status: 200 });
}

export async function DELETE({ request, cookies }: { request: Request; cookies: Cookies }) {
	let token = cookies.get('token');
	let { userId, notificationId }: { userId: number; notificationId: number } = await request.json();

	if (token == undefined || token == '' || token == null) {
		console.error('No token provided for reading notification');
		return json({ error: 'No token provided' }, { status: 400 });
	}
	if (userId == undefined) {
		console.error('No userId provided for reading notifications');
		return json({ error: 'No userId provided' }, { status: 400 });
	}
	if (notificationId == undefined) {
		console.error('No notificationId provided for reading notifications');
		return json({ error: 'No notificationId provided' }, { status: 400 });
	}

	let readNotifications = await deleteNotification(token, userId, notificationId);

	return json(readNotifications, { status: 200 });
}
