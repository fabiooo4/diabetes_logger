import { PUBLIC_API_BASE } from '$env/static/public';
import type { Report } from '$lib/types';

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

export async function getAllPatientReports(
  token: string | undefined,
  patientId: number | undefined
): Promise<Report[]> {
  if (!token) {
    console.error('No token provided for fetching patient reports');
    return [];
  }

  if (patientId == undefined) {
    console.error('No patientId provided for fetching patient reports');
    return [];
  }

  return fetch(PUBLIC_API_BASE + '/reports/patient/' + patientId, {
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
  report: Partial<Omit<Report, 'id' | 'patient'>>,
  userId: number | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/reports/user/' + userId, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(report)
  })
}

export async function editReport(
  token: string | undefined,
  report: Partial<Omit<Report, 'patient'>>,
  userId: number | undefined
): Promise<Response> {
  return fetch(PUBLIC_API_BASE + '/reports/user/' + userId + "/" + report.id, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    },
    body: JSON.stringify(report)
  })
}

export async function deleteReport(
  token: string | undefined,
  reportId: number,
  userId: number | undefined
): Promise<Report | null> {
  if (!token) {
    console.error('No token provided for deleting report');
    return null;
  }

  if (userId == undefined) {
    console.error('No userId provided for deleting report');
    return null;
  }

  return fetch(PUBLIC_API_BASE + '/reports/user/' + userId + '/' + reportId, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    }
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to delete report: ' + response.status);
      }
      return response.json();
    })
    .then((data: Report) => {
      return data;
    })
    .catch((error) => {
      console.error('Error deleting report:', error);
      return null;
    });
}
