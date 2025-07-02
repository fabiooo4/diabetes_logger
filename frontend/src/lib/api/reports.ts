import { PUBLIC_API_BASE } from '$env/static/public';
import type { Patient, Report } from '$lib/types';

export async function getAllUserReports(
  token: string | undefined,
  userId: number | undefined
): Promise<Report[]> {
  if (!token) {
    console.error('No token provided for creating report');
    return [];
  }

  if (userId == undefined) {
    console.error('No userId provided for fetching reports');
    return [];
  }

  return fetch(PUBLIC_API_BASE + '/reports/user/' + userId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to fetch reports: ' + response.statusText);
      }

      return response.json();
    })
    .then((data: Report[]) => {
      return data;
    })
    .catch((error) => {
      console.error('Error fetching reports:', error);
      return [];
    });
}

export async function createReport(
  token: string | undefined,
  report: Omit<Report, 'id' | 'patient'>,
  userId: number | undefined
): Promise<Report | null> {
  if (!token) {
    console.error('No token provided for creating report');
    return null;
  }

  if (userId == undefined) {
    console.error('No userId provided for creating report');
    return null;
  }

  console.log("caca", JSON.stringify(report));

  return fetch(PUBLIC_API_BASE + '/reports/user/' + userId, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(report)
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to create report: ' + response.status);
      }
      return response.json();
    })
    .then((data: Report) => {
      return data;
    })
    .catch((error) => {
      console.error('Error creating report:', error);
      return null;
    });
}
