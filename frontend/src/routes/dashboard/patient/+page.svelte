<script lang="ts">
	import type { Notification, Report, User } from '$lib/types';
	import AddReportDialog from './AddReportDialog.svelte';
	import ReferralMedicCard from './ReferralMedicCard.svelte';
	import ReportsCard from './ReportsCard.svelte';
	import TherapyCard from './TherapyCard.svelte';

	let {
		data
	}: {
		data: {
			user: User;
			reports: Promise<Report[]>;
			notifications: Promise<Notification[]>;
		};
	} = $props();
</script>

<main class="flex h-full w-full flex-col items-center p-4 gap-8">
	<div class="flex w-full flex-col">
		<div
			class="bg-background-alt shadow-card border-muted grid w-full grid-cols-2 gap-8 rounded-xl border p-8"
		>
			<ReferralMedicCard referralMedic={data.user.patient.referralMedic} />

			<TherapyCard therapy={data.user.patient.therapy} />
		</div>
	</div>

	<div class="flex w-full h-full flex-col">
		<div
			class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8"
		>
      <ReportsCard reports={data.reports} />
		</div>
	</div>

</main>
