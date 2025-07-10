<script lang="ts">
	import type { User } from '$lib/types';
	import { ScrollArea } from 'bits-ui';
	import UserEntry from './UserEntry.svelte';
	import AddUserDialog from './AddUserDialog.svelte';

	let {
		users,
		currentUser,
		form
	}: { users: Promise<User[]>; currentUser: User; form?: { error: string } } = $props();
</script>

<div class="flex flex-col gap-y-8">
	<div class="flex w-full flex-row items-center justify-between">
		<h1 class="text-2xl font-bold">Users</h1>
		<AddUserDialog {form} />
	</div>

	<ScrollArea.Root
		class="border-muted bg-background shadow-card relative overflow-hidden rounded-[10px] border px-4 py-4"
	>
		<ScrollArea.Viewport class="h-full max-h-183 w-full">
			{#await users}
				<p>Loading users...</p>
			{:then users}
				<div class="flex flex-col gap-y-2">
					{#if users.length === 0}
						<p class="text-muted-foreground">No reports available.</p>
					{:else}
						{#each users.filter((user) => user.id !== currentUser.id && user.verified) as user}
							<UserEntry {user} {form} />
						{/each}
					{/if}
				</div>
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
</div>
