import type { Actions, PageServerLoad } from './$types';
import { editPatient, getAllPatients } from '$lib/api/patients';
import { fail } from '@sveltejs/kit';
import type { Patient } from '$lib/types';

export const load: PageServerLoad = ({ locals, cookies }) => {
  let token = cookies.get('token');

  let patients = getAllPatients(token);

  return {
    user: locals.user,
    patients: patients
  };
};

export const actions: Actions = {
  editPatient: async ({ request, cookies }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for editing report');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();

    const patient: Pick<
      Partial<Patient>,
      'id' | 'riskFactor' | 'previousPatologies' | 'medicNotes' | 'therapy'
    > = {
      id: parseInt(form.get('patientId') as string),
      riskFactor: form.get('riskFactor') as string,
      previousPatologies: form.get('previousPatologies') as string,
      medicNotes: form.get('medicNotes') as string
    };

    const res = await editPatient(token, patient);

    if (res == null) {
      console.error('Failed to edit patient');
      return fail(500, { error: 'Failed to edit patient' });
    }
  }
};
