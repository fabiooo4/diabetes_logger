<script lang="ts">
	import type { Patient, User } from '$lib/types';

	let { data }: { data: { user: User; patientsList: Promise<Patient[]> } } = $props();
</script>

<main class="flex h-full flex-col p-4">
	<h1>{JSON.stringify(data.user)}</h1>

	<div class="mt-8 flex">
		{#await data.patientsList}
			<!-- promise is pending -->
			<p>Loading patients...</p>
		{:then patients}
			<!-- promise was fulfilled -->
			<ul class="flex flex-col gap-y-4">
				{#each patients as patient}
					<li>
						{JSON.stringify(patient)}
					</li>
				{/each}
			</ul>
		{/await}
	</div>
</main>
