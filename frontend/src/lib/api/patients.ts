import { PUBLIC_API_BASE } from '$env/static/public';
import type { Patient } from '$lib/types';

export async function getAllPatients(token: string | undefined): Promise<Patient[]> {
  return fetch(PUBLIC_API_BASE + '/patients', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to fetch patients: ' + response.statusText);
      }

      return response.json();
    })
    .then((data: Patient[]) => {
      return data;
    })
    .catch((error) => {
      console.error('Error fetching patients:', error);
      return [];
    });
}
