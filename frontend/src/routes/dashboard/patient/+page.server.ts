import type { PageServerLoad } from './$types';
import { createReport, getAllUserReports } from '$lib/api/reports';
import { getAllUserNotifications } from '$lib/api/notifications';
import { fail, type Actions } from '@sveltejs/kit';
import type { Report } from '$lib/types';
import { parseDateTime } from '@internationalized/date';

export const load: PageServerLoad = ({ locals, cookies }) => {
	let token = cookies.get('token');

	let reportsList = getAllUserReports(token, locals.user?.id);
	let notificationsList = getAllUserNotifications(token, locals.user?.id);

	return {
		user: locals.user,
		reportsList: reportsList,
		notificationsList: notificationsList
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
		if (typeof date !== 'string' || typeof time !== 'string') {
			return fail(400, { error: 'Invalid date or time format' });
		}

		const dateTime = parseDateTime(date + 'T' + time).toString();

		if (dateTime == null) {
			return fail(400, { error: 'Invalid date or time format' });
		}

		const report: Omit<Report, 'id' | 'patient'> = {
			glycemiaLevel: parseFloat(form.get('glycemiaLevel') as string),
			beforeMeal: form.get('beforeMeal') === 'true',
			dateTime: dateTime,
			symptoms: form.get('symptoms') as string,
			notes: form.get('notes') as string,
			medicine: form.get('medicine') as string,
			amount: parseFloat(form.get('amount') as string)
		};

		if (
			report.dateTime == null ||
			isNaN(report.glycemiaLevel) ||
			isNaN(report.amount) ||
			report.medicine == null ||
			report.beforeMeal == null
		) {
			return fail(400, { error: 'Invalid form data' });
		}

		const res = await createReport(token, report, locals.user?.id);

		if (res == null) {
			console.error('Failed to create report');
			return fail(500, { error: 'Failed to create report' });
		}
	}
};
