<script lang="ts">
	import type { User } from '$lib/types';

	let { data }: { data: { user: User; userList: Promise<User[]> } } = $props();
</script>

<main class="flex h-full flex-col p-4">
	<h1>{JSON.stringify(data.user)}</h1>

	<div class="mt-8 flex">
		{#await data.userList}
			<!-- promise is pending -->
			<p>Loading users...</p>
		{:then users}
			<!-- promise was fulfilled -->
			<ul class="flex flex-col gap-y-4">
				{#each users as user}
					<li>
						{user.id} - {user.email} - {user.role}<br />
						{#if user.role === 'PATIENT'}
							{JSON.stringify(user.patient)}
						{:else if user.role === 'MEDIC'}
							{JSON.stringify(user.medic)}
						{/if}
					</li>
				{/each}
			</ul>
		{/await}
	</div>
</main>
