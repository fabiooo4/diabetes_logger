import { deleteUser, getAllPendingUsers, verifyUser } from '$lib/api/users';
import { json, type Cookies } from '@sveltejs/kit';

export async function GET({ cookies }: { cookies: Cookies }) {
  let token = cookies.get('token');

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for reading notification');
    return json({ error: 'No token provided' }, { status: 400 });
  }

  let pending = await getAllPendingUsers(token);

  return json(pending);
}

export async function DELETE({ request, cookies }: { request: Request; cookies: Cookies }) {
  let token = cookies.get('token');
  let { userId }: { userId: number } = await request.json();

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for reading notification');
    return json({ error: 'No token provided' }, { status: 400 });
  }
  if (userId == undefined) {
    console.error('No userId provided for reading notifications');
    return json({ error: 'No userId provided' }, { status: 400 });
  }

  let res = await deleteUser(userId, token);
  if (!res.ok) {
    console.error('Failed to delete user:', res.statusText);
    return json({ error: await res.text() }, { status: res.status });
  }

  return json({ success: true }, { status: 200 });
}

export async function PATCH({ request, cookies }: { request: Request; cookies: Cookies }) {
  let token = cookies.get('token');
  let { userId }: { userId: number } = await request.json();

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for reading notification');
    return json({ error: 'No token provided' }, { status: 400 });
  }
  if (userId == undefined) {
    console.error('No userId provided for reading notifications');
    return json({ error: 'No userId provided' }, { status: 400 });
  }

  let res = await verifyUser(token, userId);

  if (!res.ok) {
    console.error('Failed to verify user:', res.statusText);
    return json({ error: await res.text() }, { status: res.status });
  }

  return json({ success: true }, { status: 200 });
}
