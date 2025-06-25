<script lang="ts">
	import { Button } from 'bits-ui';
  import { login } from '$lib/api/auth';

	let email = $state('');
	let password = $state('');
</script>

<main class="flex items-center justify-center">
	<div class="mt-16 flex flex-col items-center justify-center gap-4">
		<input
			type="text"
			name="email"
			placeholder="email"
			bind:value={email}
			class="rounded-input shadow-mini border p-2 outline-none"
		/>
		<input
			type="text"
			name="password"
			placeholder="password"
			bind:value={password}
			class="rounded-input shadow-mini border p-2 outline-none"
		/>

		<Button.Root
      onclick={() => login(email, password)}
			class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 inline-flex h-12
      items-center justify-center self-end px-[21px] text-[15px]
      font-semibold active:scale-[0.98] active:transition-all"
		>
			Login
		</Button.Root>
	</div>
</main>
