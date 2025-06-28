import type { PageServerLoad } from './$types';
import { getAllPatients } from '$lib/api/patients';

export const load: PageServerLoad = ({ locals, cookies }) => {
  let token = cookies.get('token');

  let patientsList = getAllPatients(token);

  return {
    user: locals.user,
    patientsList: patientsList
  };
};
