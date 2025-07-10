<script lang="ts">
	import '../app.css';
	import Sun from 'phosphor-svelte/lib/Sun';
	import Moon from 'phosphor-svelte/lib/Moon';
	import { Avatar, DropdownMenu } from 'bits-ui';
	import Syringe from 'phosphor-svelte/lib/Syringe';
	import UserCircle from 'phosphor-svelte/lib/UserCircle';
	import SignOut from 'phosphor-svelte/lib/SignOut';
	import { browser } from '$app/environment';
	import type { Notification, User } from '$lib/types';
	import type { Snippet } from 'svelte';
	import { page } from '$app/state';
	import { goto } from '$app/navigation';
	import NotificationsButton from './NotificationsButton.svelte';
	import VerificationRequestsButton from './dashboard/admin/VerificationRequestsButton.svelte';

	let {
		children,
		data
	}: {
		children: Snippet<[]>;
		data: { user: User; notifications: Promise<Notification[]>; token: string };
	} = $props();

	type Theme = 'dark' | 'light';

	const initialTheme: boolean = browser && localStorage.getItem('theme') === 'dark' ? true : false;
	let isDarkMode = $state(initialTheme);

	$effect(() => {
		if (browser) {
			let theme: Theme = isDarkMode ? 'dark' : 'light';

			localStorage.setItem('theme', theme);
			document.documentElement.classList.toggle('dark', isDarkMode);
		}
	});

	function toggleTheme() {
		isDarkMode = !isDarkMode;
	}
</script>

<div class="flex h-screen flex-col">
	<header
		class="bg-background-alt shadow-card top-0 flex w-full flex-row items-center gap-4 border-b px-4 py-2"
	>
		{#if page.url.pathname.startsWith('/login') || page.url.pathname === '/register' || page.error || page.url.pathname === '/dashboard/profile/changedProfile'}
			<h1 class="text-foreground text-lg font-bold">Diabetes Logger</h1>
		{:else}
			<!-- Profile -->
			<div class="flex flex-row items-center gap-4">
				<DropdownMenu.Root>
					<DropdownMenu.Trigger
						class="border-input text-foreground shadow-btn hover:bg-muted inline-flex size-12 items-center justify-center rounded-full text-sm font-medium select-none active:scale-[0.98]"
					>
						<Avatar.Root
							loadingStatus="loaded"
							class="data-[status=loaded]:border-foreground bg-muted text-muted-foreground size-12 cursor-pointer rounded-full border text-[17px] font-medium uppercase data-[status=loading]:border-transparent"
						>
							<div
								class="flex h-full w-full items-center justify-center overflow-hidden rounded-full border-2 border-transparent"
							>
								<Avatar.Image src="/avatar.png" alt="@huntabyte" />
								<Avatar.Fallback class="border-muted border">Pr</Avatar.Fallback>
							</div>
						</Avatar.Root>
					</DropdownMenu.Trigger>
					<DropdownMenu.Portal>
						<DropdownMenu.Content
							class="border-muted bg-background shadow-popover w-[229px] rounded-xl border px-1 py-1.5 outline-hidden focus-visible:outline-hidden"
							sideOffset={8}
						>
							{#if !page.url.pathname.startsWith('/dashboard') || page.url.pathname === '/dashboard/profile'}
								<DropdownMenu.Item
									class="rounded-button data-highlighted:bg-muted flex h-10 cursor-pointer items-center py-3 pr-1.5 pl-3 text-sm font-medium ring-0! ring-transparent! select-none focus-visible:outline-none"
									onSelect={() => {
										goto('/dashboard');
									}}
								>
									<div class="flex items-center">
										<Syringe class="text-foreground-alt mr-2 size-5" />
										Dashboard
									</div>
								</DropdownMenu.Item>
							{/if}
							{#if page.url.pathname.startsWith('/dashboard') && page.url.pathname !== '/dashboard/profile' && data.user != null}
								<DropdownMenu.Item
									class="rounded-button data-highlighted:bg-muted flex h-10 cursor-pointer items-center py-3 pr-1.5 pl-3 text-sm font-medium ring-0! ring-transparent! select-none focus-visible:outline-none"
									onSelect={() => {
										goto('profile');
									}}
								>
									<div class="flex items-center">
										<UserCircle class="text-foreground-alt mr-2 size-5" />
										Profile
									</div>
								</DropdownMenu.Item>
							{/if}
							<DropdownMenu.CheckboxItem
								bind:checked={isDarkMode}
								class="rounded-button data-highlighted:bg-muted flex h-10 cursor-pointer items-center py-3 pr-1.5 pl-3 text-sm font-medium ring-0! ring-transparent! select-none focus-visible:outline-none"
							>
								{#snippet children()}
									<div class="flex w-full flex-row items-center justify-between">
										<div class="flex flex-row items-center">
											{#if isDarkMode}
												<Sun class="text-foreground-alt mr-2 size-5" />
												Light Mode
											{:else}
												<Moon class="text-foreground-alt mr-2 size-5" />
												Dark Mode
											{/if}
										</div>
									</div>
								{/snippet}
							</DropdownMenu.CheckboxItem>
							<DropdownMenu.Item
								class="rounded-button data-highlighted:bg-muted flex h-10 cursor-pointer items-center py-3 pr-1.5 pl-3 text-sm font-medium ring-0! ring-transparent! select-none focus-visible:outline-none"
								onSelect={() => {
									goto('/logout');
								}}
							>
								<div class="text-destructive flex items-center">
									<SignOut class="text-destructive mr-2 size-5" />
									Logout
								</div>
							</DropdownMenu.Item>
						</DropdownMenu.Content>
					</DropdownMenu.Portal>
				</DropdownMenu.Root>

				{#if data.user != null}
					{#if data.user.medic != null}
						<h1 class="text-foreground text-lg font-bold">{data.user.medic.firstName}</h1>
					{:else if data.user.patient != null}
						<h1 class="text-foreground text-lg font-bold">{data.user.patient.firstName}</h1>
					{:else}
						<h1 class="text-foreground text-lg font-bold">{data.user.email}</h1>
					{/if}
				{/if}
			</div>
		{/if}

		<!-- Middle content -->
		<div class="ml-auto flex items-center gap-4">
			{#if page.url.pathname !== '/login' && page.url.pathname !== '/register' && !page.error && data.user != null && data.user.role != 'ADMIN'}
				<NotificationsButton notifications={data.notifications} userId={data.user?.id} />
			{/if}

      {#if page.url.pathname.startsWith('/dashboard/admin') && data.user.role === 'ADMIN'}
        <VerificationRequestsButton />
      {/if}

			<!-- Dark Mode Toggle -->
			{#if page.url.pathname === '/login' || page.url.pathname === '/register' || page.error || data.user == null}
				<button
					onclick={toggleTheme}
					class="hover:bg-muted focus:bg-muted dark:hover:bg-muted rounded-xl p-2 transition-all focus:outline-none"
				>
					{#if isDarkMode}
						<Sun class="text-foreground size-9" />
					{:else}
						<Moon class="text-foreground size-9" />
					{/if}
				</button>
			{/if}
		</div>
	</header>

	{@render children()}
</div>
