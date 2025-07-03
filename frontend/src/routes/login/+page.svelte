<script lang="ts">
	import { Button } from 'bits-ui';
	import { enhance } from '$app/forms';
	import LockKeyOpen from 'phosphor-svelte/lib/LockKeyOpen';
	import LockKey from 'phosphor-svelte/lib/LockKey';

	let { form }: { form?: { error: string } } = $props();

	let showPassword = $state(false);
</script>

<main class="flex items-center justify-center">
	<div class="flex flex-col">
		<form
			method="POST"
			action="?/login"
			use:enhance
			class="rounded-card border-muted bg-background-alt shadow-card mt-16 flex w-[390px] flex-col items-center justify-center gap-4 border p-3"
		>
			<h1 class="w-full text-center text-2xl font-bold">Login</h1>

			{#if form?.error}
				<p class="font-bold text-red-500">{form.error}</p>
			{/if}

			<div class="flex w-5/6 flex-col items-center justify-center gap-4">
				<div class="w-full">
					<label for="email" class="pb-1 pl-1 text-sm">Email</label>
					<input
						type="email"
						name="email"
						class="transition-all ring-transparent h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
					/>
				</div>

				<div class="relative w-full">
					<label for="password" class="pb-1 pl-1 text-sm">Password</label>
					<input
						type={showPassword ? 'text' : 'password'}
						name="password"
						class="transition-all ring-transparent h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
					/>
					<button type="button" onclick={() => (showPassword = !showPassword)}>
            {#if showPassword}
              <LockKeyOpen class="text-dark/30 absolute top-9 right-4 size-6 hover:bg-muted transition rounded cursor-pointer" />
            {:else}
              <LockKey class="text-dark/30 absolute top-9 right-4 size-6 hover:bg-muted transition rounded cursor-pointer" />
            {/if}
					</button>
				</div>
			</div>

			<Button.Root
				type="submit"
				class="transition-all rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12
        items-center justify-center self-end px-[21px] text-[15px]
        font-semibold active:scale-[0.98] active:transition-all"
			>
				Login
			</Button.Root>
		</form>
		<p class="p-1">
			Don't have an account?
			<a href="/register" class="underline">Register</a>
		</p>
	</div>
</main>
