<script lang="ts">
	import { Tabs } from 'bits-ui';
	import type { Component } from 'svelte';

	const props: { patientForm: Component; medicForm: Component; role: string } = $props();
</script>

<Tabs.Root value={props.role}>
	<input type="hidden" name="role" value={props.role} />
	<Tabs.List
		class="rounded-9px bg-dark-10 shadow-mini-inset dark:bg-background grid w-full grid-cols-2 gap-1 p-1 text-sm leading-[0.01em] font-semibold dark:border dark:border-neutral-600/30"
	>
		<Tabs.Trigger
			value="PATIENT"
			class="data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
			>Patient</Tabs.Trigger
		>
		<Tabs.Trigger
			value="MEDIC"
			class="data-[state=active]:shadow-mini dark:data-[state=active]:bg-muted h-8 rounded-[7px] bg-transparent py-2 data-[state=active]:bg-white"
			>Medic</Tabs.Trigger
		>
	</Tabs.List>
	<Tabs.Content value="PATIENT" class="pt-3 select-none">
		<props.patientForm role={props.role} />
	</Tabs.Content>
	<Tabs.Content value="MEDIC" class="pt-3 select-none">
		<props.medicForm role={props.role} />
	</Tabs.Content>
</Tabs.Root>
