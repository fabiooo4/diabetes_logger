import type { PageServerLoad } from './$types';
import { createReport, deleteReport, editReport, getAllUserReports } from '$lib/api/reports';
import { getAllUserNotifications } from '$lib/api/notifications';
import { fail, type Actions } from '@sveltejs/kit';
import type { Report } from '$lib/types';
import { parseDateTime } from '@internationalized/date';

export const load: PageServerLoad = ({ locals, cookies }) => {
  let token = cookies.get('token');

  let reports = getAllUserReports(token, locals.user?.id);
  let notifications = getAllUserNotifications(token, locals.user?.id);

  return {
    user: locals.user,
    reports: reports,
    notifications: notifications
  };
};

export const actions: Actions = {
  createReport: async ({ request, cookies, locals }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for creating report');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    // Merge date and time into a single dateTime string
    const date = form.get('date') as string;
    const time = form.get('time') as string;

    let dateTime: string | undefined;
    if (date == null || time == null || date === '' || time === '') {
      dateTime = undefined;
    } else {
      dateTime = parseDateTime(date + 'T' + time).toString();
    }

    const report: Partial<Omit<Report, 'id' | 'patient'>> = {
      glycemiaLevel: parseFloat(form.get('glycemiaLevel') as string)
        ? parseFloat(form.get('glycemiaLevel') as string)
        : undefined,
      beforeMeal: form.get('beforeMeal') === 'true',
      dateTime: dateTime,
      symptoms: form.get('symptoms') as string,
      notes: form.get('notes') as string,
      medicine: form.get('medicine') as string,
      amount: parseFloat(form.get('amount') as string)
        ? parseFloat(form.get('amount') as string)
        : undefined
    };

    const res = await createReport(token, report, locals.user?.id);
    if (!res.ok) {
      return fail(res.status, { error: await res.text() });
    }
  },

  editReport: async ({ request, cookies, locals }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for editing report');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    // Merge date and time into a single dateTime string
    const date = form.get('date') as string;
    const time = form.get('time') as string;

    let dateTime: string | undefined;
    if (date == null || time == null || date === '' || time === '') {
      dateTime = undefined;
    } else {
      dateTime = parseDateTime(date + 'T' + time).toString();
    }

    const report: Partial<Omit<Report, 'patient'>> = {
      id: parseInt(form.get('reportId') as string),
      glycemiaLevel: parseFloat(form.get('glycemiaLevel') as string)
        ? parseFloat(form.get('glycemiaLevel') as string)
        : undefined,
      beforeMeal: form.get('beforeMeal') === 'true',
      dateTime: dateTime,
      symptoms: form.get('symptoms') as string,
      notes: form.get('notes') as string,
      medicine: form.get('medicine') as string,
      amount: parseFloat(form.get('amount') as string)
        ? parseFloat(form.get('amount') as string)
        : undefined
    };

    const res = await editReport(token, report, locals.user?.id);
    if (!res.ok) {
      return fail(res.status, { error: await res.text() });
    }
  },

  deleteReport: async ({ request, cookies, locals }) => {
    const token = cookies.get('token');

    if (!token) {
      console.error('No token provided for deleting report');
      return fail(401, { error: 'Unauthorized' });
    }

    const form = await request.formData();
    const reportId = parseInt(form.get('reportId') as string);

    if (isNaN(reportId)) {
      return fail(400, { error: 'Invalid form data' });
    }

    const res = await deleteReport(token, reportId, locals.user?.id);

    if (res == null) {
      console.error('Failed to delete report');
      return fail(500, { error: 'Failed to delete report' });
    }
  }
};
