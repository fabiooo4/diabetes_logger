<script lang="ts">
	import type { Medic } from '$lib/types';

	let { referralMedic }: { referralMedic: Medic } = $props();
</script>

<div
	class="bg-background shadow-card border-muted flex flex-col items-center justify-center gap-y-4 rounded-xl border p-6"
>
	<h1 class="text-foreground text-2xl font-bold">Referral medic</h1>
	{#if referralMedic}
		<div class="flex flex-col gap-y-2">
			<div class="flex flex-row gap-x-8 flex-wrap">
				<div>
					<h2 class="text-foreground-alt">First name</h2>
					<p class="text-foreground text-md">
						{referralMedic.firstName}
					</p>
				</div>
				<div>
					<h2 class="text-foreground-alt">Last name</h2>
					<p class="text-foreground text-md">
						{referralMedic.lastName}
					</p>
				</div>
				<div>
				<h2 class="text-foreground-alt">Email</h2>
				<p class="text-foreground text-md">
					{referralMedic.email}
				</p>
				</div>
			</div>
		</div>
	{:else}
		<h1 class="text-foreground-alt text-lg">No referral medic found</h1>
	{/if}
</div>
