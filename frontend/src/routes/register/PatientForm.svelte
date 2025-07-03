<script lang="ts">
	import { DateField } from 'bits-ui';
	import { CalendarDate, getLocalTimeZone, today } from '@internationalized/date';

	let date: CalendarDate | undefined = $state(undefined);
</script>

<div class="flex flex-col gap-4">
	<div>
		<label for="patientFirstName" class="pb-1 pl-1 text-sm">First Name</label>
		<input
			type="text"
			name="patientFirstName"
			class="transition-all ring-transparent h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
		/>
	</div>

	<div>
		<label for="patientLastName" class="pb-1 pl-1 text-sm">Last Name</label>
		<input
			type="text"
			name="patientLastName"
			class="transition-all ring-transparent h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none"
		/>
	</div>

	<div>
		<DateField.Root
			locale="en-GB"
			bind:value={date}
			maxValue={today(getLocalTimeZone())}
		>
			<input
				type="hidden"
				name="birthDate"
				value={date ? date.toString() : undefined}
			/>
			<div class="flex w-full flex-col gap-1.5">
				<DateField.Label class="block text-sm font-medium select-none">Birthday</DateField.Label>
				<DateField.Input
					class="transition-all ring-transparent h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover data-invalid:border-destructive flex w-full items-center border px-2 py-3 text-sm tracking-[0.01em] select-none "
				>
					{#snippet children({ segments })}
						{#each segments as { part, value }, i (part + i)}
							<div class="inline-block select-none">
								{#if part === 'literal'}
									<DateField.Segment {part} class="text-muted-foreground p-1">
										{value}
									</DateField.Segment>
								{:else}
									<DateField.Segment
										{part}
										class="rounded-5px hover:bg-muted focus:bg-muted focus:text-foreground aria-[valuetext=Empty]:text-muted-foreground data-invalid:text-destructive px-1 py-1 focus-visible:ring-0! focus-visible:ring-offset-0!"
									>
										{value}
									</DateField.Segment>
								{/if}
							</div>
						{/each}
					{/snippet}
				</DateField.Input>
			</div>
		</DateField.Root>
	</div>
</div>
