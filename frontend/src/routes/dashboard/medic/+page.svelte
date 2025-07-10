<script lang="ts">
	import type { Patient, User } from '$lib/types';
	import PatientsCard from './PatientsCard.svelte';

	let {
		data,
		form
	}: { data: { user: User; patients: Promise<Patient[]> }; form?: { error: string } } = $props();
</script>

<main class="flex h-full w-full flex-col items-center gap-8 p-4">
	<div class="flex h-full w-full flex-col">
		<div class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8">
			<PatientsCard patients={data.patients} user={data.user} {form} />
		</div>
	</div>
</main>
