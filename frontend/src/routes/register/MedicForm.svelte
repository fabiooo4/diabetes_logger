<script lang="ts">
	import type { User } from "$lib/types";

	let { user }: { user?: User } = $props();
</script>

<div class="flex flex-col gap-4">
	<div>
		<label for="medicFirstName" class="pb-1 pl-1 text-sm">First Name</label>
		<input
			type="text"
			name="medicFirstName"
			class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
      value={user ? user.medic?.firstName : undefined}
		/>
	</div>

	<div>
		<label for="medicLastName" class="pb-1 pl-1 text-sm">Last Name</label>
		<input
			type="text"
			name="medicLastName"
			class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
      value={user ? user.medic?.lastName : undefined}
		/>
	</div>
</div>
