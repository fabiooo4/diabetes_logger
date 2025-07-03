<script lang="ts">
	import '../app.css';
	import Sun from 'phosphor-svelte/lib/Sun';
	import Moon from 'phosphor-svelte/lib/Moon';

	let { children } = $props();

	import { browser } from '$app/environment';

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

<button
	onclick={toggleTheme}
	class="rounded-full p-2 transition-all hover:bg-muted focus:bg-muted focus:outline-none dark:hover:bg-muted"
>
	{#if theme === 'dark'}
		<Sun class="text-foreground size-9" />
	{:else}
		<Moon class="text-foreground size-9" />
	{/if}
</button>

{@render children()}
