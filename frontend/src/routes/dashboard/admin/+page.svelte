<script lang="ts">
	import type { User } from '$lib/types';
	import UsersCard from './UsersCard.svelte';

	let { data, form }: { data: { user: User; userList: Promise<User[]> }; form?: {error: string} } = $props();
</script>

<main class="flex h-full flex-col p-4">
	<div class="mt-8 flex">
    <div class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8">
      <UsersCard users={data.userList} currentUser={data.user} {form} />
    </div>
	</div>
</main>
