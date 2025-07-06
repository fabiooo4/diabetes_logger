<script lang="ts">
	import { goto } from '$app/navigation';
	import { Button } from 'bits-ui';
	import ArrowLeft from 'phosphor-svelte/lib/ArrowLeft';
</script>

<div class="flex h-full flex-col items-center justify-center">
	<div class="bg-background-alt shadow-card border-muted w-full max-w-md rounded-xl border p-8">
		<div class="mb-6 flex flex-col items-center">
      <h1 class="text-foreground text-2xl font-bold">Profile changed successfully</h1>
			<p class="text-foreground-alt mt-2 text-center text-lg">
				Please sign in again with the new credentials
			</p>
		</div>
		<div class="flex w-full">
			<Button.Root
				onclick={() => goto('/login')}
				class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12 w-full
        items-center justify-center self-end px-[21px] text-[15px]
        font-semibold transition-all active:scale-[0.98] active:transition-all"
			>
				<ArrowLeft class="mr-2 size-5" />
				Sign In Again
			</Button.Root>
		</div>
	</div>
</div>
