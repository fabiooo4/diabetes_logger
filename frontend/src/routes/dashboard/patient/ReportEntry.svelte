<script lang="ts">
	import type { Report, Role } from '$lib/types';
	import CaretDown from 'phosphor-svelte/lib/CaretDown';
	import Trash from 'phosphor-svelte/lib/Trash';
	import PencilSimple from 'phosphor-svelte/lib/PencilSimple';
	import { Accordion, TimeField, DatePicker, Switch, Dialog, Label, Separator } from 'bits-ui';
	import X from 'phosphor-svelte/lib/X';
	import {
		CalendarDate,
		CalendarDateTime,
		parseDate,
		parseDateTime
	} from '@internationalized/date';
	import CalendarBlank from 'phosphor-svelte/lib/CalendarBlank';
	import CaretLeft from 'phosphor-svelte/lib/CaretLeft';
	import CaretRight from 'phosphor-svelte/lib/CaretRight';
	import Asterisk from 'phosphor-svelte/lib/Asterisk';
	import { enhance } from '$app/forms';

	let { report, role, form }: { report: Report; role: Role; form?: { error: string } } = $props();

	let dateStr = report.dateTime.split('T')[0];
	let date: CalendarDate = $state(parseDate(dateStr));
	let time: CalendarDateTime = $state(parseDateTime(report.dateTime));
	let beforeMeal: boolean = $state(report.beforeMeal);

	let disabled = $state(report.symptoms === '' && report.notes === '');
</script>

<div
	class="bg-background-alt shadow-card border-muted flex w-full flex-row items-center gap-8 rounded-xl border p-4"
>
	<Accordion.Root class="w-full" type="multiple">
		<Accordion.Item value="1" class="border-dark-10 group px-1.5">
			<Accordion.Header class="relative">
				{#if role === 'PATIENT'}
					<div class="absolute top-2 right-12 cursor-default">
						<Dialog.Root>
							<Dialog.Trigger
								class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent transition-all"
							>
								<Trash class="text-destructive size-[18px]" />
							</Dialog.Trigger>
							<Dialog.Portal>
								<Dialog.Overlay
									class="data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 fixed inset-0 z-50 bg-black/80"
								/>
								<Dialog.Content
									class="rounded-card-lg bg-background shadow-popover data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 fixed top-[50%] left-[50%] z-50 w-full max-w-[calc(100%-2rem)] translate-x-[-50%] translate-y-[-50%] border p-5 outline-hidden sm:max-w-[490px] md:w-full"
								>
									<Dialog.Title
										class="flex w-full items-center justify-center text-lg font-semibold tracking-tight"
									>
										Delete report
									</Dialog.Title>
									<Separator.Root class="bg-muted -mx-5 mt-5 mb-6 block h-px" />
									<Dialog.Description class="text-foreground-alt mb-6 text-sm">
										Are you sure you want to delete this report? This action cannot be undone.
									</Dialog.Description>
									<div class="flex w-full justify-end">
										<form method="POST" action="?/deleteReport">
											<input type="hidden" bind:value={report.id} name="reportId" />
											<Dialog.Close
												class="h-input rounded-input bg-destructive text-foreground shadow-mini hover:bg-destructive/95 focus-visible:ring-dark focus-visible:ring-offset-background inline-flex items-center justify-center px-[50px] text-[15px] font-semibold focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
												type="submit"
											>
												Confirm
											</Dialog.Close>
										</form>
									</div>
									<Dialog.Close
										class="focus-visible:ring-foreground focus-visible:ring-offset-background absolute top-5 right-5 rounded-md focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
									>
										<div>
											<X class="text-foreground size-5" />
											<span class="sr-only">Close</span>
										</div>
									</Dialog.Close>
								</Dialog.Content>
							</Dialog.Portal>
						</Dialog.Root>

						<Dialog.Root>
							<Dialog.Trigger
								onclick={() => {
									if (form) {
										form.error = '';
									}
								}}
								class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent transition-all"
							>
								<PencilSimple class="text-foreground size-[18px]" />
							</Dialog.Trigger>
							<Dialog.Portal>
								<Dialog.Overlay
									class="data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 fixed inset-0 z-50 bg-black/80"
								/>
								<Dialog.Content
									class="rounded-card-lg bg-background shadow-popover data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 fixed top-[50%] left-[50%] z-50 w-full max-w-[calc(100%-2rem)] translate-x-[-50%] translate-y-[-50%] border p-5 outline-hidden sm:max-w-[490px] md:w-full"
								>
									<Dialog.Title
										class="flex w-full items-center justify-center text-lg font-semibold tracking-tight"
									>
										Edit report
									</Dialog.Title>
									<Separator.Root class="bg-muted -mx-5 mt-5 mb-6 block h-px" />
									<Dialog.Description class="text-destructive font-bold">
										{form?.error}
									</Dialog.Description>
									<form action="?/editReport" method="POST" use:enhance>
										<input type="hidden" name="reportId" value={report.id} />
										<div class="flex flex-col items-start gap-1 pt-7 pb-11">
											<div class="flex w-full flex-row items-start gap-2">
												<DatePicker.Root
													weekdayFormat="short"
													locale="en-GB"
													fixedWeeks={true}
													bind:value={date}
												>
													<div class="flex w-full max-w-[232px] flex-col gap-1.5">
														<DatePicker.Label
															class="relative block w-fit text-sm font-medium select-none"
															>Date
															<Asterisk
																class="text-destructive absolute -top-0 -right-3 size-[13px]"
															/>
														</DatePicker.Label>
														<input type="hidden" name="date" value={date.toString()} />
														<DatePicker.Input
															class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover flex w-full max-w-[232px] items-center border px-2 py-3 text-sm tracking-[0.01em] transition-all select-none"
														>
															{#snippet children({ segments })}
																{#each segments as { part, value }, i (part + i)}
																	<div class="inline-block select-none">
																		{#if part === 'literal'}
																			<DatePicker.Segment {part} class="text-muted-foreground p-1">
																				{value}
																			</DatePicker.Segment>
																		{:else}
																			<DatePicker.Segment
																				{part}
																				class="rounded-5px hover:bg-muted focus:bg-muted focus:text-foreground aria-[valuetext=Empty]:text-muted-foreground px-1 py-1 focus-visible:ring-0! focus-visible:ring-offset-0!"
																			>
																				{value}
																			</DatePicker.Segment>
																		{/if}
																	</div>
																{/each}
																<DatePicker.Trigger
																	class="text-foreground/60 hover:bg-muted active:bg-dark-10 ml-auto inline-flex size-8 items-center justify-center rounded-[5px] transition-all"
																>
																	<CalendarBlank class="size-6" />
																</DatePicker.Trigger>
															{/snippet}
														</DatePicker.Input>
														<DatePicker.Content sideOffset={6} class="z-50">
															<DatePicker.Calendar
																class="border-dark-10 bg-background-alt shadow-popover rounded-[15px] border p-[22px]"
															>
																{#snippet children({ months, weekdays })}
																	<DatePicker.Header class="flex items-center justify-between">
																		<DatePicker.PrevButton
																			class="rounded-9px bg-background-alt hover:bg-muted inline-flex size-10 items-center justify-center transition-all active:scale-[0.98]"
																		>
																			<CaretLeft class="size-6" />
																		</DatePicker.PrevButton>
																		<DatePicker.Heading class="text-[15px] font-medium" />
																		<DatePicker.NextButton
																			class="rounded-9px bg-background-alt hover:bg-muted inline-flex size-10 items-center justify-center transition-all active:scale-[0.98]"
																		>
																			<CaretRight class="size-6" />
																		</DatePicker.NextButton>
																	</DatePicker.Header>
																	<div
																		class="flex flex-col space-y-4 pt-4 sm:flex-row sm:space-y-0 sm:space-x-4"
																	>
																		{#each months as month (month.value)}
																			<DatePicker.Grid
																				class="w-full border-collapse space-y-1 select-none"
																			>
																				<DatePicker.GridHead>
																					<DatePicker.GridRow
																						class="mb-1 flex w-full justify-between"
																					>
																						{#each weekdays as day (day)}
																							<DatePicker.HeadCell
																								class="text-muted-foreground w-10 rounded-md text-xs font-normal!"
																							>
																								<div>{day.slice(0, 2)}</div>
																							</DatePicker.HeadCell>
																						{/each}
																					</DatePicker.GridRow>
																				</DatePicker.GridHead>
																				<DatePicker.GridBody>
																					{#each month.weeks as weekDates (weekDates)}
																						<DatePicker.GridRow class="flex w-full">
																							{#each weekDates as date (date)}
																								<DatePicker.Cell
																									{date}
																									month={month.value}
																									class="relative size-10 p-0! text-center text-sm"
																								>
																									<DatePicker.Day
																										class="rounded-9px text-foreground hover:border-foreground data-selected:bg-foreground data-disabled:text-foreground/30 data-selected:text-background data-unavailable:text-muted-foreground group relative inline-flex size-10 items-center justify-center border border-transparent bg-transparent p-0 text-sm font-normal whitespace-nowrap transition-all data-disabled:pointer-events-none data-outside-month:pointer-events-none data-selected:font-medium data-unavailable:line-through"
																									>
																										<div
																											class="bg-foreground group-data-selected:bg-background absolute top-[5px] hidden size-1 rounded-full transition-all group-data-today:block"
																										></div>
																										{date.day}
																									</DatePicker.Day>
																								</DatePicker.Cell>
																							{/each}
																						</DatePicker.GridRow>
																					{/each}
																				</DatePicker.GridBody>
																			</DatePicker.Grid>
																		{/each}
																	</div>
																{/snippet}
															</DatePicker.Calendar>
														</DatePicker.Content>
													</div>
												</DatePicker.Root>
												<TimeField.Root bind:value={time} hideTimeZone={true}>
													<div class="flex w-full max-w-[280px] flex-col gap-1.5">
														<TimeField.Label
															class="relative block w-fit text-sm font-medium select-none"
															>Time
															<Asterisk
																class="text-destructive absolute -top-0 -right-3 size-[13px]"
															/>
														</TimeField.Label>
														<TimeField.Input
															name="time"
															class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover data-invalid:border-destructive flex w-full items-center border px-2 py-3 text-sm tracking-[0.01em] transition-all select-none "
														>
															{#snippet children({ segments })}
																{#each segments as { part, value }, i (part + i)}
																	<div class="inline-block select-none">
																		{#if part === 'literal'}
																			<TimeField.Segment {part} class="text-muted-foreground p-1">
																				{value}
																			</TimeField.Segment>
																		{:else}
																			<TimeField.Segment
																				{part}
																				class="rounded-5px hover:bg-muted focus:bg-muted focus:text-foreground aria-[valuetext=Empty]:text-muted-foreground data-invalid:text-destructive px-1 py-1 focus-visible:ring-0! focus-visible:ring-offset-0!"
																			>
																				{value}
																			</TimeField.Segment>
																		{/if}
																	</div>
																{/each}
															{/snippet}
														</TimeField.Input>
													</div>
												</TimeField.Root>
											</div>

											<div class="my-3 flex items-center space-x-3">
												<input type="hidden" name="beforeMeal" bind:value={beforeMeal} />
												<Switch.Root
													id="beforeMeal"
													bind:checked={beforeMeal}
													class="focus-visible:ring-foreground focus-visible:ring-offset-background data-[state=checked]:bg-foreground data-[state=unchecked]:bg-dark-10 data-[state=unchecked]:shadow-mini-inset dark:data-[state=checked]:bg-foreground peer inline-flex h-[36px] min-h-[36px] w-[60px] shrink-0 cursor-pointer items-center rounded-full px-[3px] ring-transparent transition-colors focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden disabled:cursor-not-allowed disabled:opacity-50"
												>
													<Switch.Thumb
														class="bg-background data-[state=unchecked]:shadow-mini dark:border-background/30 dark:bg-foreground dark:shadow-popover pointer-events-none block size-[30px] shrink-0 rounded-full transition-transform data-[state=checked]:translate-x-6 data-[state=unchecked]:translate-x-0 dark:border dark:data-[state=unchecked]:border"
													/>
												</Switch.Root>
												<Label.Root class="text-sm font-medium">Before meal</Label.Root>
											</div>

											<Label.Root class="relative text-sm font-medium"
												>Glycemia level
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="glycemiaLevel"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													type="number"
													placeholder="Enter glycemia level"
													value={report.glycemiaLevel}
													name="glycemiaLevel"
												/>
												<!-- Possible icon -->
												<!-- <LockKeyOpen class="text-dark/30 absolute top-[14px] right-4 size-[22px]" /> -->
											</div>

											<Label.Root class="text-sm font-medium">Symptoms</Label.Root>
											<div class="relative w-full">
												<textarea
													id="symptoms"
													class="rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex h-[6rem] w-full resize-none items-center border px-2 py-3 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
													placeholder="Describe your symptoms"
													value={report.symptoms}
													name="symptoms"
												></textarea>
											</div>

											<Label.Root class="text-sm font-medium">Extra notes</Label.Root>
											<div class="relative w-full">
												<textarea
													id="notes"
													class="rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex h-[6rem] w-full resize-none items-center border px-2 py-3 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
													placeholder="Add any extra notes, like other therapies you are taking or pathologies you currently have"
													value={report.notes}
													name="notes"
												></textarea>
											</div>

											<Label.Root class="relative text-sm font-medium"
												>Medicine
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="medicine"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													placeholder="Insert the medicine you report taking"
													value={report.medicine}
													name="medicine"
												/>
											</div>

											<Label.Root for="amount" class="relative text-sm font-medium"
												>Medicine amount
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="amount"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													type="number"
													placeholder="Insert the amount of medicine you report taking"
													value={report.amount}
													name="amount"
												/>
											</div>
										</div>
										<div class="flex w-full justify-end">
											<button
												type="submit"
												class="h-input rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 focus-visible:ring-dark focus-visible:ring-offset-background inline-flex items-center justify-center px-[50px] text-[15px] font-semibold ring-transparent focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
												>Save</button
											>
										</div>
									</form>
									<Dialog.Close
										class="focus-visible:ring-foreground focus-visible:ring-offset-background absolute top-5 right-5 rounded-md focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
									>
										<div>
											<X class="text-foreground size-5" />
											<span class="sr-only">Close</span>
										</div>
									</Dialog.Close>
								</Dialog.Content>
							</Dialog.Portal>
						</Dialog.Root>
					</div>
				{/if}
				<Accordion.Trigger
					{disabled}
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

					{#if !disabled}
						<span
							class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent transition-all"
						>
							<CaretDown class="size-[18px] transition-transform duration-200" />
						</span>
					{/if}
				</Accordion.Trigger>
			</Accordion.Header>
			<Accordion.Content
				class="data-[state=closed]:animate-accordion-up data-[state=open]:animate-accordion-down mt-4 overflow-hidden tracking-[-0.01em]"
			>
				<div class="flex flex-col gap-y-4">
					{#if report.symptoms}
						<div>
							<h1 class="text-foreground-alt">Symptoms:</h1>
							<p>
								{report.symptoms}
							</p>
						</div>
					{/if}
					{#if report.notes}
						<div>
							<h1 class="text-foreground-alt">Notes:</h1>
							<p>
								{report.notes}
							</p>
						</div>
					{/if}
				</div>
			</Accordion.Content>
		</Accordion.Item>
	</Accordion.Root>
</div>
