<script lang="ts">
	import type { Notification, Report, User } from '$lib/types';
	import PatientReports from './PatientReports.svelte';
	import ReferralMedicCard from './ReferralMedicCard.svelte';
	import TherapyCard from './TherapyCard.svelte';

	let {
		data,
		form
	}: {
		data: {
			user: User;
			reports: Promise<Report[]>;
			notifications: Promise<Notification[]>;
		};
		form?: { error: string };
	} = $props();
</script>

<main class="flex h-full w-full flex-col items-center gap-8 p-4">
	<div class="flex w-full flex-col">
		<div
			class="bg-background-alt shadow-card border-muted grid w-full grid-cols-2 gap-8 rounded-xl border p-8"
		>
			<ReferralMedicCard referralMedic={data.user.patient.referralMedic} />

			<TherapyCard therapy={data.user.patient.therapy} />
		</div>
	</div>

	<div class="flex h-full w-full flex-col">
		<div class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8">
			<PatientReports reports={data.reports} {form}/>
		</div>
	</div>
</main>
