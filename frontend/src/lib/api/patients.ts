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

export async function editPatient(
  token: string | undefined,
  patient: Pick<
    Partial<Patient>,
    'id' | 'riskFactor' | 'previousPatologies' | 'medicNotes' | 'therapy'
  >,
): Promise<Report | null> {
  if (!token) {
    console.error('No token provided for editing patient');
    return null;
  }

  return fetch(PUBLIC_API_BASE + '/patients/' + patient.id, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(patient)
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to edit patient: ' + response.status);
      }
      return response.json();
    })
    .then((data: Report) => {
      return data;
    })
    .catch((error) => {
      console.error('Error editing patient:', error);
      return null;
    });
}
