<script lang="ts">
	import { Button, Separator } from 'bits-ui';
	import { enhance } from '$app/forms';
	import RoleTabs from './RoleTabs.svelte';
	import MedicForm from './MedicForm.svelte';
	import PatientForm from './PatientForm.svelte';

	let role = $state('PATIENT');
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

			<div class="flex w-5/6 flex-col items-center justify-center gap-4">
				<div class="w-full">
					<label for="email" class="pb-1 pl-1 text-sm">Email</label>
					<input
						type="email"
						name="email"
						class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
					/>
				</div>

				<div class="w-full">
					<label for="password" class="pb-1 pl-1 text-sm">Password</label>
					<input
						type="password"
						name="password"
						class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
					/>
				</div>

				<Separator.Root
					class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
				/>

				<div class="w-full">
					<RoleTabs patientForm={PatientForm} medicForm={MedicForm} {role} />
				</div>
			</div>

			<Button.Root
				type="submit"
				class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 m-3 inline-flex h-12
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
