<script lang="ts">
	import type { User } from '$lib/types';

	let { data }: { data: { user: User } } = $props();
</script>

<main>
	<h1>Hello {data.user.email}</h1>
</main>
