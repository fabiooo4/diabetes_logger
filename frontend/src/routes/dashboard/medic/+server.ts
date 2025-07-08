import { getAllPatientReports } from '$lib/api/reports';
import { json, type Cookies } from '@sveltejs/kit';

export async function POST({ request, cookies }: { request: Request; cookies: Cookies }) {
  let token = cookies.get('token');
  let patientId: number = await request.json().then((data) => data.patientId);

  if (token == undefined || token == '' || token == null) {
    console.error('No token provided for reading notification');
    return json({ error: 'No token provided' }, { status: 400 });
  }
  if (patientId == undefined) {
    console.error('No userId provided for reading notifications');
    return json({ error: 'No userId provided' }, { status: 400 });
  }

  let reports = await getAllPatientReports(token, patientId);

  return json(reports);
}
