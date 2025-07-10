<script lang="ts">
	import { enhance } from '$app/forms';
	import UserForm from './UserForm.svelte';

	let { form }: { form?: { error: string } } = $props();
</script>

<main class="flex h-full items-center justify-center">
	<div
		class="rounded-card border-muted bg-background-alt shadow-card flex w-[390px] flex-col items-center justify-center gap-4 border p-3"
	>
		<h1 class="w-full text-center text-2xl font-bold">Register</h1>
		<form
			method="POST"
			action="?/register"
			use:enhance
			class="flex h-full w-full flex-col items-center justify-center"
		>
			<UserForm {form} />
		</form>
	</div>
</main>
