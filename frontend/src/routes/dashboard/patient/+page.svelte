<script lang="ts">
	import type { Notification, Report, User } from '$lib/types';
	import AddReportDialog from './AddReportDialog.svelte';

	let {
		data
	}: {
		data: {
			user: User;
			reportsList: Promise<Report[]>;
			notificationsList: Promise<Notification[]>;
		};
	} = $props();
</script>

<main class="flex h-full flex-col items-center p-4">
	<h1>{JSON.stringify(data.user)}</h1>

	<AddReportDialog />

	<div class="p-2">
		{#await data.reportsList}
			<!-- promise is pending -->
			<p>Loading reports...</p>
		{:then reports}
			<!-- promise was fulfilled -->
			{#if reports.length == 0}
				<p>No reports found.</p>
			{:else}
				<ul class="flex flex-col gap-y-4 wrap-anywhere">
					{#each reports as report}
						<li>
							{JSON.stringify(report)}
						</li>
					{/each}
				</ul>
			{/if}
		{/await}
	</div>
</main>
