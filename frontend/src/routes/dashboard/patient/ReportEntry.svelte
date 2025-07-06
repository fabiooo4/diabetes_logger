<script lang="ts">
	import type { Report } from '$lib/types';
	import { Accordion, Separator } from 'bits-ui';
	import CaretDown from 'phosphor-svelte/lib/CaretDown';

	let { report }: { report: Report } = $props();
</script>

<div
	class="bg-background-alt shadow-card border-muted flex w-full flex-row items-center gap-8 rounded-xl border p-4"
>
	<Accordion.Root class="w-full" type="multiple">
		<Accordion.Item value="1" class="border-dark-10 group px-1.5">
			<Accordion.Header>
				<Accordion.Trigger
					class="flex w-full flex-1 items-center justify-between transition-all select-none [&[data-state=open]>span>svg]:rotate-180"
				>
					<div class="flex w-full flex-row items-center gap-x-8">
            <div class="flex flex-col items-center">
              <p class="text-foreground-alt text-xs">
                {new Date(report.dateTime).toLocaleDateString()}
              </p>
              <p class="text-foreground-alt text-xs">
                {new Date(report.dateTime).toLocaleTimeString()}
              </p>
              <p class="text-foreground-alt text-xs">
                {report.beforeMeal ? 'Before Meal' : 'After Meal'}
              </p>
            </div>
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Glycemia:</h1>
							<p>
								{report.glycemiaLevel}
							</p>
						</div>
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Medicine:</h1>
							<p>
								{report.medicine}
							</p>
						</div>
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Amount:</h1>
							<p>
								{report.amount}
							</p>
						</div>
					</div>
					<span
						class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent"
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
            <h1 class="text-foreground-alt">Symptoms:</h1>
            <p>
              {report.symptoms}
            </p>
          </div>
          <div>
            <h1 class="text-foreground-alt">Notes:</h1>
            <p>
              {report.notes}
            </p>
          </div>
        </div>
			</Accordion.Content>
		</Accordion.Item>
	</Accordion.Root>
</div>
