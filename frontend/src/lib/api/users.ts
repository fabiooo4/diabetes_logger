import { PUBLIC_API_BASE } from '$env/static/public';
import type { User } from '$lib/types';

export async function getAllUsers(token: string | undefined): Promise<User[]> {
  return fetch(PUBLIC_API_BASE + '/users', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        console.error('Failed to fetch users:', response.statusText);
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

export async function updateUser(
  id: number | undefined,
  user: NestedPartial<User>,
  token: string | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/users/' + id, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(user)
  });
}

export async function createUser(
  token: string | undefined,
  user: NestedPartial<User>
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(user)
  });
}

export async function verifyUser(
  token: string | undefined,
  userId: number | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/users/pending/' + userId, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  });
}

export async function editUser(
  id: number | undefined,
  user: NestedPartial<User>,
  token: string | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/users/' + id, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(user)
  });
}

export async function deleteUser(
  userId: number | undefined,
  token: string | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/users/' + userId, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  });
}

export async function getAllPendingUsers(
  token: string | undefined
): Promise<User[]> {
  return fetch(PUBLIC_API_BASE + '/users/pending', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        console.error('Failed to fetch pending users:', response.statusText);
        throw new Error('Failed to fetch pending users: ' + response.statusText);
      }

      return response.json();
    })
    .then((data: User[]) => {
      return data;
    })
    .catch((error) => {
      console.error('Error fetching pending users:', error);
      return [];
    });
}
