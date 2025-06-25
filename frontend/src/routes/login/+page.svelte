<script lang="ts">
	import { Button } from 'bits-ui';
	import { enhance } from '$app/forms';
</script>

<main class="flex items-center justify-center">
	<form
		method="POST"
		action="?/login"
		use:enhance
		class="mt-16 flex flex-col items-center justify-center gap-4"
	>
		<input
			type="text"
			name="email"
			placeholder="email"
			class="rounded-input shadow-mini border p-2 outline-none"
		/>
		<input
			type="text"
			name="password"
			placeholder="password"
			class="rounded-input shadow-mini border p-2 outline-none"
		/>

		<Button.Root
			type="submit"
			class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 inline-flex h-12
      items-center justify-center self-end px-[21px] text-[15px]
      font-semibold active:scale-[0.98] active:transition-all"
		>
			Login
		</Button.Root>
	</form>
</main>
