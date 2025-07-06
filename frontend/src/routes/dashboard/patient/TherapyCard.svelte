<script lang="ts">
	import type { Therapy } from '$lib/types';

	let { therapy }: { therapy: Therapy } = $props();
</script>

<div
	class="bg-background shadow-card border-muted flex flex-col items-center justify-center gap-y-4 rounded-xl border p-6"
>
	<h1 class="text-foreground text-2xl font-bold">Therapy</h1>
	{#if therapy}
		<div class="flex flex-col gap-y-2">
			<div class="flex flex-row flex-wrap gap-x-8">
				<div>
					<h2 class="text-foreground-alt">Medicine</h2>
					<p class="text-foreground text-md">
						{therapy.medicine}
					</p>
				</div>
				<div>
					<h2 class="text-foreground-alt">Dosage</h2>
					<p class="text-foreground text-md">
						{therapy.amount}
					</p>
				</div>
				<div>
					<h2 class="text-foreground-alt">Daily intake</h2>
					<p class="text-foreground text-md">
						{therapy.dailyIntake}
					</p>
				</div>
				<div>
					<h2 class="text-foreground-alt">Directions</h2>
					<p class="text-foreground text-md">
						{therapy.directions}
					</p>
				</div>
			</div>
		</div>
	{:else}
		<h1 class="text-muted-foreground text-lg">No therapy found</h1>
	{/if}
</div>
