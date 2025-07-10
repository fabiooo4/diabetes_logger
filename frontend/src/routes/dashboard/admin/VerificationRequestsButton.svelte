<script lang="ts">
	import type { User } from '$lib/types';
	import { Popover, Separator, ScrollArea } from 'bits-ui';
	import SealCheck from 'phosphor-svelte/lib/SealCheck';
	import SealWarning from 'phosphor-svelte/lib/SealWarning';
	import X from 'phosphor-svelte/lib/X';
	import Check from 'phosphor-svelte/lib/Check';
	import { onMount } from 'svelte';

	let verifications: Promise<User[]> = $state(Promise.resolve([]));
	let isOpen = $state(false);

	onMount(() => {
		fetchPendingUsers();
	});

	async function fetchPendingUsers() {
		fetch('/dashboard/admin', {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		})
			.then((response) => {
				if (!response.ok) {
					console.error('Failed to fetch pending users' + response.statusText);
					return;
				}
				return response.json();
			})
			.then((data) => {
				verifications = Promise.resolve(data);
			})
			.catch((error) => {
				console.error('Error fetching pending users:', error);
			});
	}

	async function deletePendingUser(userId: number) {
		if (!userId) {
			console.error('No user ID found in the event target.');
			return;
		}

		await fetch('/dashboard/admin', {
			method: 'DELETE',
			body: JSON.stringify({ userId })
		});

		let fulfilled = await verifications;
		verifications = Promise.resolve(fulfilled.filter((n) => n.id !== userId));
	}

	async function verifyPendingUser(userId: number) {
		if (!userId) {
			console.error('No user ID found in the event target.');
			return;
		}

		await fetch('/dashboard/admin', {
			method: 'PATCH',
			body: JSON.stringify({ userId })
		});

		let fulfilled = await verifications;
		verifications = Promise.resolve(fulfilled.filter((n) => n.id !== userId));
	}
</script>

<Popover.Root bind:open={isOpen}>
	<Popover.Trigger
		onclick={fetchPendingUsers}
		class="rounded-input bg-dark
	text-background shadow-mini hover:bg-dark/95 inline-flex h-10 items-center justify-center p-2 text-[15px] font-medium whitespace-nowrap transition-all select-none hover:cursor-pointer active:scale-[0.98]"
	>
		{#await verifications}
			<SealCheck class="size-6" />
		{:then verifications}
			{#if verifications.length > 0}
				<SealWarning class="size-6" />
			{:else}
				<SealCheck class="size-6" />
			{/if}
		{/await}
	</Popover.Trigger>
	<Popover.Portal>
		<Popover.Content
			class="border-dark-10 bg-background-alt shadow-popover data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2 z-30 w-150 origin-(--bits-popover-content-transform-origin) rounded-[12px] border"
			sideOffset={8}
		>
			<div class="flex w-full items-center p-4">
				<div class="flex flex-col">
					<h4 class="text-[17px] leading-5 font-semibold tracking-[-0.01em]">Pending users</h4>
					<p class="text-muted-foreground text-sm font-medium">
						Verify or delete pending users before they can log in.
					</p>
				</div>
			</div>
			<ScrollArea.Root
				class="bg-background-alt relative w-full overflow-hidden rounded-b-[12px] border-t px-4"
			>
				<ScrollArea.Viewport class="h-full max-h-100 w-full">
					{#await verifications}
						<p>Loading pending users...</p>
					{:then verifications}
						{#if verifications === undefined || verifications.length === 0}
							<p class="py-4">No pending users found.</p>
						{:else}
							<div class="flex flex-col gap-y-1 wrap-anywhere">
								{#each verifications as pendingUser}
									{#if pendingUser === verifications[0]}
										<div class="mt-4">
											<div class="flex flex-row items-center justify-between gap-x-4">
												<div>
													<p class="text-sm font-medium">
														{pendingUser.role} - {pendingUser.email}
														{#if pendingUser.role === 'PATIENT'}
															{pendingUser.patient.firstName}
															{pendingUser.patient.lastName}
															{new Date(pendingUser.patient.birthDate).toLocaleDateString()}
														{:else if pendingUser.role === 'MEDIC'}
															{pendingUser.medic.firstName}
															{pendingUser.medic.lastName}
														{/if}
													</p>
												</div>
												<div class="flex flex-row items-center gap-x-2">
													<button
														onclick={async () => await deletePendingUser(pendingUser.id)}
														class="cursor-pointer"
													>
														<X class="text-background bg-destructive size-5 min-w-5 rounded" />
													</button>
													<button
														onclick={async () => await verifyPendingUser(pendingUser.id)}
														class="cursor-pointer"
													>
														<Check class="text-background size-5 min-w-5 rounded bg-green-300" />
													</button>
												</div>
											</div>
											{#if pendingUser === verifications[verifications.length - 1]}
												<Separator.Root
													class="mt-4 shrink-0 bg-transparent data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
												/>
											{:else}
												<Separator.Root
													class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
												/>
											{/if}
										</div>
									{:else}
										<div>
											<div class="flex flex-row items-center justify-between gap-x-4">
												<div>
													<p class="text-sm font-medium">
														{pendingUser.role} - {pendingUser.email}
														{#if pendingUser.role === 'PATIENT'}
															{pendingUser.patient.firstName}
															{pendingUser.patient.lastName}
															{new Date(pendingUser.patient.birthDate).toLocaleDateString()}
														{:else if pendingUser.role === 'MEDIC'}
															{pendingUser.medic.firstName}
															{pendingUser.medic.lastName}
														{/if}
													</p>
												</div>
												<div class="flex flex-row items-center gap-x-2">
													<button
														onclick={async () => await deletePendingUser(pendingUser.id)}
														class="cursor-pointer"
													>
														<X class="text-background bg-destructive size-5 min-w-5 rounded" />
													</button>
													<button
														onclick={async () => await verifyPendingUser(pendingUser.id)}
														class="cursor-pointer"
													>
														<Check class="text-background size-5 min-w-5 rounded bg-green-300" />
													</button>
												</div>
											</div>

											{#if pendingUser === verifications[verifications.length - 1]}
												<Separator.Root
													class="mt-4 shrink-0 bg-transparent data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
												/>
											{:else}
												<Separator.Root
													class="bg-border my-4 shrink-0 data-[orientation=horizontal]:h-px data-[orientation=horizontal]:w-full data-[orientation=vertical]:h-full data-[orientation=vertical]:w-[1px]"
												/>
											{/if}
										</div>
									{/if}
								{/each}
							</div>
						{/if}
					{/await}
				</ScrollArea.Viewport>
				<ScrollArea.Scrollbar
					orientation="vertical"
					class="bg-muted hover:bg-dark-10 data-[state=visible]:animate-in data-[state=hidden]:animate-out data-[state=hidden]:fade-out-0 data-[state=visible]:fade-in-0 flex w-2.5 touch-none rounded-full border-l border-l-transparent p-px transition-all duration-200 select-none hover:w-3"
				>
					<ScrollArea.Thumb class="bg-muted-foreground flex-1 rounded-full" />
				</ScrollArea.Scrollbar>
				<ScrollArea.Scrollbar
					orientation="horizontal"
					class="bg-muted hover:bg-dark-10 flex h-2.5 touch-none rounded-full border-t border-t-transparent p-px transition-all duration-200 select-none hover:h-3 "
				>
					<ScrollArea.Thumb class="bg-muted-foreground rounded-full" />
				</ScrollArea.Scrollbar>
				<ScrollArea.Corner />
			</ScrollArea.Root>
		</Popover.Content>
	</Popover.Portal>
</Popover.Root>
