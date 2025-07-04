<script lang="ts">
	import '../app.css';
	import Sun from 'phosphor-svelte/lib/Sun';
	import Moon from 'phosphor-svelte/lib/Moon';
	import { Avatar, DropdownMenu } from 'bits-ui';
	import Syringe from 'phosphor-svelte/lib/Syringe';
	import UserCircle from 'phosphor-svelte/lib/UserCircle';
	import SignOut from 'phosphor-svelte/lib/SignOut';
	import { browser } from '$app/environment';
	import type { User } from '$lib/types';
	import type { Snippet } from 'svelte';
	import { page } from '$app/state';
	import { goto } from '$app/navigation';

	let { children, data }: { children: Snippet<[]>; data: { user: User } } = $props();

	type Theme = 'dark' | 'light';

	const initialTheme: Theme =
		browser && localStorage.getItem('theme') === 'dark' ? 'dark' : 'light';

	let theme = $state<Theme>(initialTheme);

	$effect(() => {
		if (browser) {
			localStorage.setItem('theme', theme);
			document.documentElement.classList.toggle('dark', theme === 'dark');
		}
	});

	function toggleTheme() {
		theme = theme === 'dark' ? 'light' : 'dark';
	}
</script>

<div class="flex h-screen flex-col">
	<header
		class="bg-background-alt shadow-card top-0 flex w-full flex-row items-center border-b px-4 py-2"
	>
		{#if page.url.pathname === '/login' || page.url.pathname === '/register' || page.error}
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
							<DropdownMenu.Item
								class="rounded-button data-highlighted:bg-muted flex h-10 cursor-pointer items-center py-3 pr-1.5 pl-3 text-sm font-medium ring-0! ring-transparent! select-none focus-visible:outline-none"
								onSelect={() => {
									goto('/logout');
								}}
							>
								<div class="flex items-center">
									<SignOut class="text-foreground-alt mr-2 size-5" />
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

		<!-- Dark Mode Toggle -->
		<button
			onclick={toggleTheme}
			class="hover:bg-muted focus:bg-muted dark:hover:bg-muted ml-auto rounded-xl p-2 transition-all focus:outline-none"
		>
			{#if theme === 'dark'}
				<Sun class="text-foreground size-9" />
			{:else}
				<Moon class="text-foreground size-9" />
			{/if}
		</button>
	</header>

	{@render children()}
</div>
