<script lang="ts">
	import type { Report } from '$lib/types';
	import { ScrollArea } from 'bits-ui';
	import ReportEntry from './ReportEntry.svelte';
	import AddReportDialog from './AddReportDialog.svelte';

	let { reports }: { reports: Promise<Report[]> } = $props();
</script>

<div class="flex flex-col gap-y-8">
	<div class="flex w-full flex-row items-center justify-between">
		<h1 class="text-2xl font-bold">Reports</h1>
		<AddReportDialog />
	</div>

	<ScrollArea.Root
		class="border-muted bg-background shadow-card relative overflow-hidden rounded-[10px] border px-4 py-4"
	>
		<ScrollArea.Viewport class="h-full max-h-116 w-full">
			{#await reports}
				<p>Loading reports...</p>
			{:then reports}
        <div class="flex flex-col gap-y-2">
          {#if reports.length === 0}
            <p class="text-muted-foreground">No reports available.</p>
          {:else}
            {#each reports.sort((a, b) => new Date(b.dateTime).getTime() - new Date(a.dateTime).getTime()) as report}
              <ReportEntry {report} />
            {/each}
          {/if}
        </div>
			{/await}
		</ScrollArea.Viewport>
		<ScrollArea.Scrollbar
			orientation="vertical"
			class="bg-muted hover:bg-dark-10 data-[state=visible]:animate-in data-[state=hidden]:animate-out data-[state=hidden]:fade-out-0 data-[state=visible]:fade-in-0 flex w-2.5 touch-none rounded-full border-l border-l-transparent p-px transition-all duration-200 select-none hover:w-3"
		>
			<ScrollArea.Thumb class="bg-muted-foreground flex-1 rounded-full" />
		</ScrollArea.Scrollbar>
		<ScrollArea.Scrollbar
			orientation="horizontal"
			class="bg-muted hover:bg-dark-10 flex h-2.5 touch-none rounded-full border-t border-t-transparent p-px transition-all duration-200 select-none hover:h-3 "
		>
			<ScrollArea.Thumb class="bg-muted-foreground rounded-full" />
		</ScrollArea.Scrollbar>
		<ScrollArea.Corner />
	</ScrollArea.Root>
</div>
