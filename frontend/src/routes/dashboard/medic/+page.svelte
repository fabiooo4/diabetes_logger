<script lang="ts">
	import type { Patient, User } from '$lib/types';
	import PatientsCard from './PatientsCard.svelte';

	let { data }: { data: { user: User; patients: Promise<Patient[]> } } = $props();
</script>

<main class="flex h-full w-full flex-col items-center p-4 gap-8">
	<div class="flex w-full h-full flex-col">
		<div
			class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8"
		>
      <PatientsCard patients={data.patients} user={data.user} />
		</div>
	</div>
</main>
