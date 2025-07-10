<script lang="ts">
	import { goto } from '$app/navigation';
	import { Button } from 'bits-ui';
	import ArrowLeft from 'phosphor-svelte/lib/ArrowLeft';
</script>

<div class="flex h-full flex-col items-center justify-center">
	<div class="bg-background-alt shadow-card border-muted w-full max-w-md rounded-xl border p-8">
		<div class="mb-4 flex items-center justify-center">
			<h1 class="text-foreground text-2xl font-bold">Your account is not verified</h1>
		</div>
		<div class="mb-6 flex flex-col items-center">
			<p class="text-foreground-alt mt-2 text-center text-lg">
				Please wait for your account to be verified by an admin before you can log in.
			</p>
		</div>
		<div class="flex w-full">
			<Button.Root
				onclick={() => goto('/')}
				class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12 w-full
        items-center justify-center self-end px-[21px] text-[15px]
        font-semibold transition-all active:scale-[0.98] active:transition-all"
			>
				<ArrowLeft class="mr-2 size-5" />
				Go back home
			</Button.Root>
		</div>
	</div>
</div>
