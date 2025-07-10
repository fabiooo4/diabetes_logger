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
  editPatient: async ({ request, cookies, locals }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for editing report');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    const hasTherapy = form.get('therapy') === 'on';
    const referralMedicId: number = parseInt(form.get('referralMedicId') as string);

    const patient: Pick<
      NestedPartial<Patient>,
      'id' | 'riskFactor' | 'previousPatologies' | 'medicNotes' | 'therapy' | 'referralMedic'
    > = {
      id: parseInt(form.get('patientId') as string),

      riskFactor: form.get('riskFactor') as string == '' ? undefined : form.get('riskFactor') as string,
      previousPatologies: form.get('previousPatologies') as string == '' ? undefined : form.get('previousPatologies') as string,
      medicNotes: form.get('medicNotes') as string == '' ? undefined : form.get('medicNotes') as string,
      referralMedic: !isNaN(referralMedicId)
        ? {
          id: referralMedicId
        }
        : undefined,
      therapy: hasTherapy
        ? {
          id: 0,
          medicine: form.get('medicine') as string,
          dailyIntake: parseInt(form.get('dailyIntake') as string),
          amount: parseInt(form.get('amount') as string),
          directions: form.get('directions') as string
        }
        : undefined
    };

    const res = await editPatient(token, locals.user?.medic.id, patient);

    if (res == null) {
      console.error('Failed to edit patient');
      return fail(500, { error: 'Failed to edit patient' });
    }
  }
};
