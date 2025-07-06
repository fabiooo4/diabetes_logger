import { PUBLIC_API_BASE } from '$env/static/public';
import type { User } from '$lib/types';
import type { RequestEvent } from '@sveltejs/kit';

export async function login(email: string, password: string): Promise<Response> {
	return fetch(PUBLIC_API_BASE + '/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ email, password })
	});
}

export async function register(
	user: NestedRequiredBy<User, 'email' | 'password' | 'role'>
): Promise<Response> {
	console.log('Registering user:', user);

	return fetch(PUBLIC_API_BASE + '/register', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(user)
	});
}

export async function authenticateUser(
	event: RequestEvent<Partial<Record<string, string>>, string | null>
): Promise<User | null> {
	const { cookies } = event;

	const token: string | undefined = cookies.get('token');
	const userId: number = parseInt(cookies.get('userId') || '');

	// User is not authenticated if no token or userId is set
	if (token == undefined || isNaN(userId)) {
		return null;
	}

	// If credentials are valid a token and userId will be set
	// To validate the token a request to the API is made
	return fetch(PUBLIC_API_BASE + '/users/' + userId, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
			Authorization: 'Bearer ' + token
		}
	})
		.then((response) => {
			if (!response.ok) {
				console.error('Authentication failed with status:', response.status);
				return Error('Authentication failed');
			}
			return response.json();
		})
		.then((user: User) => {
			return user;
		})
		.catch((error) => {
			console.error('Error during authentication:', error);
			return null;
		});
}
