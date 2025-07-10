<script lang="ts">
	import type { MedicChangeLog } from '$lib/types';
	import CaretDown from 'phosphor-svelte/lib/CaretDown';
	import { Accordion } from 'bits-ui';

	let { log, form }: { log: MedicChangeLog; form?: { error: string } } = $props();
</script>

<div
	class="bg-background-alt shadow-card border-muted flex w-full flex-row items-center gap-8 rounded-xl border p-4"
>
	<Accordion.Root class="w-full" type="multiple">
		<Accordion.Item value="1" class="border-dark-10 group px-1.5">
			<Accordion.Header class="relative">
				<div class="absolute top-0 right-12 cursor-default"></div>
				<Accordion.Trigger
					class="flex w-full flex-1 items-center justify-between transition-all select-none [&[data-state=open]>span>svg]:rotate-180"
				>
					<div class="flex w-full flex-row items-center gap-x-8">
						<div class="flex flex-row gap-x-1">
							<p class="text-foreground-alt">
								{new Date(log.timestamp).toLocaleString()}
							</p>
						</div>
						<div class="flex flex-row gap-x-1">
							<p>
								<span class="text-foreground-alt">Medic</span> {log.medic.firstName}
								{log.medic.lastName} <span class="text-foreground-alt">edited patient</span> {log.patient.firstName}
								{log.patient.lastName}
							</p>
						</div>
					</div>

					<span
						class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent transition-all"
					>
						<CaretDown class="size-[18px] transition-transform duration-200" />
					</span>
				</Accordion.Trigger>
			</Accordion.Header>
			<Accordion.Content
				class="data-[state=closed]:animate-accordion-up data-[state=open]:animate-accordion-down mt-4 overflow-hidden tracking-[-0.01em]"
			>
				<div class="flex flex-col gap-y-4">
					<div>
						<p>
							{log.action}
						</p>
					</div>
				</div>
			</Accordion.Content>
		</Accordion.Item>
	</Accordion.Root>
</div>
