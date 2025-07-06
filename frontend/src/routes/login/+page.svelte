<script lang="ts">
	import { Button } from 'bits-ui';
	import { enhance } from '$app/forms';
	import LockKeyOpen from 'phosphor-svelte/lib/LockKeyOpen';
	import LockKey from 'phosphor-svelte/lib/LockKey';

	let { form }: { form?: { error: string } } = $props();

	let showPassword = $state(false);
</script>

<main class="flex h-full items-center justify-center">
	<div class="flex flex-col">
		<form
			method="POST"
			action="?/login"
			use:enhance
			class="rounded-card border-muted bg-background-alt shadow-card flex w-[390px] flex-col items-center justify-center gap-4 border p-3"
		>
			<h1 class="w-full text-center text-2xl font-bold">Login</h1>

			<div class="flex flex-col w-5/6">
				{#if form?.error}
					<p class="text-destructive font-bold mb-4">{form.error}</p>
				{/if}

				<div class="flex w-full flex-col items-center justify-center gap-4">
					<div class="w-full">
						<label for="email" class="pb-1 pl-1 text-sm">Email</label>
						<input
							type="email"
							name="email"
							class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
						/>
					</div>

					<div class="relative w-full">
						<label for="password" class="pb-1 pl-1 text-sm">Password</label>
						<input
							type={showPassword ? 'text' : 'password'}
							name="password"
							class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
						/>
						<button tabindex="-1" type="button" onclick={() => (showPassword = !showPassword)}>
							{#if showPassword}
								<LockKeyOpen
									class="text-dark/30 hover:bg-muted absolute top-9 right-4 size-6 cursor-pointer rounded transition"
								/>
							{:else}
								<LockKey
									class="text-dark/30 hover:bg-muted absolute top-9 right-4 size-6 cursor-pointer rounded transition"
								/>
							{/if}
						</button>
					</div>
				</div>

        <div class="mb-3 flex w-full flex-row items-baseline justify-between">
          <p class="p-1">
            <a href="/register" class="underline"> Don't have an account? </a>
          </p>
          <Button.Root
            type="submit"
            class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 inline-flex h-12 items-center
            justify-center self-end px-[21px] text-[15px] font-semibold
            transition-all active:scale-[0.98] active:transition-all"
          >
            Login
          </Button.Root>
        </div>
			</div>

		</form>
	</div>
</main>
