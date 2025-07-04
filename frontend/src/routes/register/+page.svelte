<script lang="ts">
	import { Button, Separator, Tabs } from 'bits-ui';
	import { enhance } from '$app/forms';
	import MedicForm from './MedicForm.svelte';
	import PatientForm from './PatientForm.svelte';
	import LockKeyOpen from 'phosphor-svelte/lib/LockKeyOpen';
	import LockKey from 'phosphor-svelte/lib/LockKey';

	let { form }: { form?: { error: string } } = $props();

	let role = $state('PATIENT');
	let showPassword = $state(false);
</script>

<main class="flex h-full items-center justify-center">
	<div class="flex flex-col">
		<form
			method="POST"
			action="?/register"
			use:enhance
			class="rounded-card border-muted bg-background-alt shadow-card flex w-[390px] flex-col items-center justify-center gap-4 border p-3"
		>
			<h1 class="w-full text-center text-2xl font-bold">Register</h1>

			{#if form?.error}
				<p class="font-bold text-red-500">{form.error}</p>
			{/if}

			<div class="flex w-5/6 flex-col items-center justify-center gap-4">
				<div class="w-full">
					<label for="email" class="pb-1 pl-1 text-sm">Email</label>
					<input
						type="email"
						name="email"
						class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
					/>
				</div>

				<div class="flex flex-col relative w-full">
					<label for="password" class="pb-1 pl-1 text-sm">Password</label>
					<input
						type={showPassword ? 'text' : 'password'}
						name="password"
						class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
					/>
					<button type="button" onclick={() => (showPassword = !showPassword)}>
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

				<Separator.Root
					class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
				/>

				<div class="w-full">
					<Tabs.Root bind:value={role} class="transition-all">
						<input type="hidden" name="role" bind:value={role} />
						<Tabs.List
							class="rounded-9px bg-dark-10 shadow-mini-inset dark:bg-background grid w-full grid-cols-2 gap-1 p-1 text-sm leading-[0.01em] font-semibold dark:border dark:border-neutral-600/30"
						>
							<Tabs.Trigger
								value="PATIENT"
								class="data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 cursor-pointer rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
								>Patient</Tabs.Trigger
							>
							<Tabs.Trigger
								value="MEDIC"
								class="data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 cursor-pointer rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
								>Medic</Tabs.Trigger
							>
						</Tabs.List>
						<Tabs.Content value="PATIENT" class="pt-3 select-none">
							<PatientForm />
						</Tabs.Content>
						<Tabs.Content value="MEDIC" class="pt-3 select-none">
							<MedicForm />
						</Tabs.Content>
					</Tabs.Root>
				</div>
			</div>

			<div class="mb-3 flex w-5/6 flex-row items-baseline justify-between">
				<p class="p-1">
					<a href="/login" class="underline"> Already have an account? </a>
				</p>
				<Button.Root
					type="submit"
					class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12 items-center
          justify-center self-end px-[21px] text-[15px] font-semibold
          transition-all active:scale-[0.98] active:transition-all"
				>
					Register
				</Button.Root>
			</div>
		</form>
	</div>
</main>
