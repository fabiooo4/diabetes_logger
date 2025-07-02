<script lang="ts">
	import type { User } from '$lib/types';
	import AddReportDialog from './AddReportDialog.svelte';

	let { data }: { data: { user: User; reportsList: Promise<Report[]> } } = $props();
</script>

<main>
	<h1>Hello {data.user.email}</h1>

  <AddReportDialog />

	<div class="mt-8 flex">
		{#await data.reportsList}
			<!-- promise is pending -->
			<p>Loading reports...</p>
		{:then reports}
			<!-- promise was fulfilled -->
			<ul class="flex flex-col gap-y-4">
				{#each reports as report}
					<li>
						{JSON.stringify(report)}
					</li>
				{/each}
			</ul>
		{/await}
	</div>
</main>
