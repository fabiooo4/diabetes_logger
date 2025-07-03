<script lang="ts">
	import { Button, Separator, Tabs } from 'bits-ui';
	import { enhance } from '$app/forms';
	import MedicForm from './MedicForm.svelte';
	import PatientForm from './PatientForm.svelte';
	import LockKeyOpen from 'phosphor-svelte/lib/LockKeyOpen';
	import LockKey from 'phosphor-svelte/lib/LockKey';

  let { form }: {form?: {error: string}} = $props();

	let role = $state('PATIENT');
	let showPassword = $state(false);
</script>

<main class="flex items-center justify-center">
	<div class="flex flex-col">
		<form
			method="POST"
			action="?/register"
			use:enhance
			class="rounded-card border-muted bg-background-alt shadow-card mt-16 flex w-[390px] flex-col items-center justify-center gap-4 border p-3"
		>
			<h1 class="w-full text-center text-2xl font-bold">Register</h1>

      {#if form?.error}
        <p class="text-red-500 font-bold">{form.error}</p>
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
								class="cursor-pointer data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
								>Patient</Tabs.Trigger
							>
							<Tabs.Trigger
								value="MEDIC"
								class="cursor-pointer data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
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

			<Button.Root
				type="submit"
				class="transition-all rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12
        items-center justify-center self-end px-[21px] text-[15px]
        font-semibold active:scale-[0.98] active:transition-all"
			>
				Register
			</Button.Root>
		</form>
		<p class="p-1">
			Already have an account?
			<a href="/login" class="underline">Login</a>
		</p>
	</div>
</main>
