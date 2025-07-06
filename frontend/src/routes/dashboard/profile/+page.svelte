<script lang="ts">
	import type { User } from '$lib/types';
	import { Separator } from 'bits-ui';
	import EditProfile from './EditProfile.svelte';

	let { data }: { data: { user: User } } = $props();
</script>

<div class="flex h-full w-full flex-col items-center justify-center">
	<div
		class="rounded-card border-muted bg-background-alt shadow-card flex w-96 flex-col border p-3"
	>
		<h1 class="text-center text-2xl font-bold">Profile</h1>

		<Separator.Root
			class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
		/>

		{#if data.user != null}
			<div class="flex flex-col gap-4 px-4 pb-2">
				<div class="flex justify-between gap-4">
					<div>
						<h2 class="text-lg font-bold">Email</h2>
						<p>{data.user.email}</p>
					</div>
				</div>
				<div>
					<h2 class="text-lg font-bold">Password</h2>
					<p>●●●●●●●●●●</p>
				</div>
				{#if data.user.role === 'PATIENT'}
					<div>
						<h2 class="text-lg font-bold">First Name</h2>
						<p>{data.user.patient.firstName}</p>
					</div>
					<div>
						<h2 class="text-lg font-bold">Last Name</h2>
						<p>{data.user.patient.lastName}</p>
					</div>
					<div>
						<h2 class="text-lg font-bold">Birth Date</h2>
						<p>{data.user.patient.birthDate}</p>
					</div>
				{:else if data.user.role === 'MEDIC'}
					<div>
						<h2 class="text-lg font-bold">First Name</h2>
						<p>{data.user.medic.firstName}</p>
					</div>
					<div>
						<h2 class="text-lg font-bold">Last Name</h2>
						<p>{data.user.medic.lastName}</p>
					</div>
				{/if}
			</div>
		{/if}

		<Separator.Root
			class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
		/>

		<EditProfile user={data.user} />
	</div>
</div>
