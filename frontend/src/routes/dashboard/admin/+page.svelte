<script lang="ts">
	import type { MedicChangeLog, User } from '$lib/types';
	import MedicChangelogCard from './MedicChangelogCard.svelte';
	import UsersCard from './UsersCard.svelte';

	let { data, form }: { data: { user: User; userList: Promise<User[]>; changelog: Promise<MedicChangeLog[]> }; form?: {error: string} } = $props();
</script>

<main class="flex h-full flex-col p-4">
	<div class="mt-8 grid grid-cols-2 gap-8">
    <div class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8">
      <UsersCard users={data.userList} currentUser={data.user} {form} />
    </div>
    <div class="bg-background-alt shadow-card border-muted w-full gap-8 rounded-xl border p-8">
      <MedicChangelogCard changelog={data.changelog} />
    </div>
	</div>
</main>
