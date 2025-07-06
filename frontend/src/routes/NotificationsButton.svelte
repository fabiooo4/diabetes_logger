<script lang="ts">
	import type { Notification } from '$lib/types';
	import { Popover, Separator, ScrollArea } from 'bits-ui';
	import Bell from 'phosphor-svelte/lib/Bell';
	import X from 'phosphor-svelte/lib/X';

	let { notifications, userId }: { notifications: Promise<Notification[]>; userId: number } =
		$props();
	let isOpen = $state(false);
	let alreadyOpened = $state(false);

	$effect(() => {
		if (isOpen) {
			if (alreadyOpened == false) alreadyOpened = true;

			fetch('/', {
				method: 'POST',
				body: JSON.stringify({ userId }),
				headers: {
					'Content-Type': 'application/json'
				}
			})
				.then((response) => {
					if (!response.ok) {
						console.error('Failed to fetch notifications' + response.statusText);
						return;
					}
					return response.json();
				})
				.then((data) => {
					notifications = Promise.resolve(data);
				})
				.catch((error) => {
					console.error('Error fetching notifications:', error);
				});
		} else {
			if (!alreadyOpened) return;

			fetch('/', {
				method: 'PATCH',
				body: JSON.stringify({ userId }),
				headers: {
					'Content-Type': 'application/json'
				}
			});
		}
	});

	async function deleteNotification(notificationId: number) {
		if (!notificationId) {
			console.error('No notification ID found in the event target.');
			return;
		}

		await fetch('/', {
			method: 'DELETE',
			body: JSON.stringify({ userId, notificationId })
		});

		let fulfilled = await notifications;
		notifications = Promise.resolve(fulfilled.filter((n) => n.id !== notificationId));
	}
</script>

<Popover.Root bind:open={isOpen}>
	<Popover.Trigger
		class="rounded-input bg-dark
	text-background shadow-mini hover:bg-dark/95 inline-flex h-10 items-center justify-center p-2 text-[15px] font-medium whitespace-nowrap transition-all select-none hover:cursor-pointer active:scale-[0.98]"
	>
		<Bell class="size-6" />
	</Popover.Trigger>
	<Popover.Portal>
		<Popover.Content
			class="border-dark-10 bg-background-alt shadow-popover data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2 z-30 w-100 origin-(--bits-popover-content-transform-origin) rounded-[12px] border"
			sideOffset={8}
		>
			<div class="flex w-full items-center p-4">
				<div class="flex flex-col">
					<h4 class="text-[17px] leading-5 font-semibold tracking-[-0.01em]">Notifications</h4>
					<p class="text-muted-foreground text-sm font-medium">Manage your notifications</p>
				</div>
			</div>
			<ScrollArea.Root
				class="bg-background-alt relative w-full overflow-hidden rounded-b-[12px] border-t px-4"
			>
				<ScrollArea.Viewport class="h-full max-h-100 w-full">
					{#await notifications}
						<p>Loading notifications...</p>
					{:then notifications}
						{#if notifications === undefined || notifications.length === 0}
							<p class="py-4">No notifications found.</p>
						{:else}
							<div class="flex flex-col gap-y-1 wrap-anywhere">
								{#each notifications.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()) as notification}
									{#if notification === notifications[0]}
										<div class="mt-4">
											<div class="flex flex-row items-center justify-between gap-x-4">
												<div>
													<p class="text-sm font-medium">{notification.message}</p>
													<p class="text-muted-foreground text-xs">
														{new Date(notification.createdAt).toLocaleString()}
														<span class="text-destructive ml-2"
															>{notification.seen ? '' : 'NEW'}</span
														>
													</p>
												</div>
												<button
													onclick={async () => await deleteNotification(notification.id)}
													class="cursor-pointer"
												>
													<X class="text-foreground size-5 min-w-5" />
												</button>
											</div>
											{#if notification === notifications[notifications.length - 1]}
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
													<p class="text-sm font-medium">{notification.message}</p>
													<p class="text-muted-foreground text-xs">
														{new Date(notification.createdAt).toLocaleString()}
														<span class="text-destructive ml-2"
															>{notification.seen ? '' : 'NEW'}</span
														>
													</p>
												</div>
												<button
													onclick={async () => await deleteNotification(notification.id)}
													class="cursor-pointer"
												>
													<X class="text-foreground size-5 min-w-5" />
												</button>
											</div>

											{#if notification === notifications[notifications.length - 1]}
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
