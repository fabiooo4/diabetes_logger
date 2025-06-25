<script lang="ts">
	import '../app.css';

  let api = $state('http://localhost:8080');

	let { children } = $props();
</script>

{@render children()}
