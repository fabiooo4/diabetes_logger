import { PUBLIC_API_BASE } from '$env/static/public';
import type { MedicChangeLog } from '$lib/types';

export async function getLatestPatientChange(
  token: string | undefined,
  patientId: number | undefined
): Promise<MedicChangeLog | null> {
  if (!token) {
    console.error('No token provided for getting latest patient change');
    return null;
  }

  if (patientId == undefined) {
    console.error('No userId provided for getting latest patient change');
    return null;
  }

  return fetch(PUBLIC_API_BASE + '/medicChangeLog/patient/' + patientId.toString(), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to get latest patient change: ' + response.statusText);
      }

      return response ? response.json() : null;
    })
    .then((data: MedicChangeLog) => {
      return data;
    })
    .catch(() => {
      return null;
    });
}

export async function getAllLogs(token: string | undefined): Promise<MedicChangeLog[] | []> {
  if (!token) {
    console.error('No token provided for getting all logs');
    return [];
  }

  return fetch(PUBLIC_API_BASE + '/medicChangeLog', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to get all logs: ' + response.statusText);
      }

      return response ? response.json() : [];
    })
    .then((data: MedicChangeLog[]) => {
      return data;
    })
    .catch(() => {
      return [];
    });
}
