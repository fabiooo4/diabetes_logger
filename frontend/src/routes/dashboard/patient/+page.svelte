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

	<div class="mt-8 grid grid-cols-2 grid-rows-1">
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
		<div class="p-2">
			{#await data.notificationsList}
				<!-- promise is pending -->
				<p>Loading notifications...</p>
			{:then notifications}
				<!-- promise was fulfilled -->
				{#if notifications.length == 0}
					<p>No notifications found.</p>
				{:else}
					<ul class="flex flex-col gap-y-4 wrap-anywhere">
						{#each notifications as notification}
							<li>
								{JSON.stringify(notification)}
							</li>
						{/each}
					</ul>
				{/if}
			{/await}
		</div>
	</div>
</main>
