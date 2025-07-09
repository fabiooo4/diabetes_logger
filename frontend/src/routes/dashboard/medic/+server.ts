import { getLatestPatientChange } from '$lib/api/medicChangeLog';
import { getAllPatientReports } from '$lib/api/reports';
import { json, type Cookies } from '@sveltejs/kit';

export async function POST({ request, cookies }: { request: Request; cookies: Cookies }) {
  let token = cookies.get('token');
  let patientId: number = await request.json().then((data) => data.patientId);

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for fetching reports');
    return json({ error: 'No token provided' }, { status: 400 });
  }
  if (patientId == undefined) {
    console.error('No userId provided for fetching reports');
    return json({ error: 'No patientId provided' }, { status: 400 });
  }

  let reports = await getAllPatientReports(token, patientId);

  return json(reports);
}

export async function PATCH({ request, cookies }: { request: Request; cookies: Cookies }) {
  let token = cookies.get('token');
  let patientId: number = await request.json().then((data) => data.patientId);

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for fetching last patient modifications');
    return json({ error: 'No token provided' }, { status: 400 });
  }
  if (patientId == undefined) {
    console.error('No userId provided for fetching last patient modifications');
    return json({ error: 'No patientId provided' }, { status: 400 });
  }

  let lastModified = await getLatestPatientChange(token, patientId);

  return json({ lastModified });
}
