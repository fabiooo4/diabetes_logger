<script lang="ts">
	import type { Report } from '$lib/types';
	import AddReportDialog from './AddReportDialog.svelte';
	import ReportsCard from './ReportsCard.svelte';

	let { reports, form }: { reports: Promise<Report[]>, form?: {error: string} } = $props();
</script>

<div class="flex flex-col gap-y-8">
	<div class="flex w-full flex-row items-center justify-between">
		<h1 class="text-2xl font-bold">Reports</h1>
		<AddReportDialog {form} />
	</div>
	<ReportsCard {reports} role="PATIENT" {form} />
</div>
