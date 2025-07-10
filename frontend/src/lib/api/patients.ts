import { PUBLIC_API_BASE } from '$env/static/public';
import type { Patient } from '$lib/types';
import { fail } from '@sveltejs/kit';

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
  medicId: number | undefined,
  patient: Pick<
    NestedPartial<Patient>,
    'id' | 'riskFactor' | 'previousPatologies' | 'medicNotes' | 'therapy' | 'referralMedic'
  >,
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/patients/medic/' + medicId + '/' + patient.id, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(patient)
  })
}
