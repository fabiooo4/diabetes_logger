<script lang="ts">
	import { DatePicker, Dialog, Label, Separator } from 'bits-ui';
	import CalendarBlank from 'phosphor-svelte/lib/CalendarBlank';
	import CaretLeft from 'phosphor-svelte/lib/CaretLeft';
	import CaretRight from 'phosphor-svelte/lib/CaretRight';
	import PencilSimple from 'phosphor-svelte/lib/PencilSimple';
	import { CalendarDate, getLocalTimeZone, parseDate, today } from '@internationalized/date';
	import X from 'phosphor-svelte/lib/X';
	import type { User } from '$lib/types';
	import LockKeyOpen from 'phosphor-svelte/lib/LockKeyOpen';
	import LockKey from 'phosphor-svelte/lib/LockKey';

	let { user }: { user: User } = $props();

	let date: CalendarDate = $state(
		user?.patient?.birthDate ? parseDate(user.patient.birthDate) : today(getLocalTimeZone())
	);
	let showPassword = $state(false);
</script>

<div class="flex w-full">
	<Dialog.Root>
		<Dialog.Trigger
			class="rounded-input bg-dark text-background
      shadow-mini hover:bg-dark/95 focus-visible:ring-foreground focus-visible:ring-offset-background inline-flex
      h-10 w-full items-center justify-center text-[15px] font-semibold whitespace-nowrap transition-colors focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
		>
			<PencilSimple class="size-6" />
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
					Edit profile
				</Dialog.Title>
				<Separator.Root class="bg-muted -mx-5 mt-5 mb-6 block h-px" />
				<form action="?/editProfile" method="POST">
          <input type="hidden" name="role" value={user?.role || ''} />

					<div class="flex flex-col items-start gap-3 pb-7">
						<div class="flex w-full flex-col gap-1.5">
							<Label.Root class="text-sm font-medium">Email</Label.Root>
							<div class="relative w-full">
								<input
									id="email"
									class="h-input rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex w-full items-center border px-4 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
									type="email"
									value={user?.email || ''}
									name="email"
								/>
							</div>
						</div>

						<div class="relative flex w-full flex-col gap-1.5">
							<Label.Root class="text-sm font-medium">Password</Label.Root>
							<div class="flex w-full flex-row">
								<input
									type={showPassword ? 'text' : 'password'}
									name="password"
									class="h-input rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex w-full items-center border px-4 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
								/>
								<button tabindex="-1" type="button" onclick={() => (showPassword = !showPassword)}>
									{#if showPassword}
										<LockKeyOpen
											class="text-dark/30 hover:bg-muted absolute top-9 right-4 size-6 cursor-pointer rounded transition"
										/>
									{:else}
										<LockKey
											class="text-dark/30 hover:bg-muted absolute top-9 right-4 size-6 cursor-pointer rounded transition"
										/>
									{/if}
								</button>
							</div>
						</div>

						{#if user !== null}
							{#if user.role === 'PATIENT' || user.role === 'MEDIC'}
								<div class="flex w-full flex-col gap-1.5">
									<Label.Root class="text-sm font-medium">First name</Label.Root>
									<div class="relative w-full">
										<input
											id="firstName"
											class="h-input rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex w-full items-center border px-4 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
											type="firstName"
											value={user.patient != null
												? user.patient.firstName
												: user.medic != null
													? user.medic.firstName
													: ''}
											name="firstName"
										/>
									</div>
								</div>

								<div class="flex w-full flex-col gap-1.5">
									<Label.Root class="text-sm font-medium">Last name</Label.Root>
									<div class="relative w-full">
										<input
											id="lastName"
											class="h-input rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex w-full items-center border px-4 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
											type="lastName"
											value={user.patient != null
												? user.patient.lastName
												: user.medic != null
													? user.medic.lastName
													: ''}
											name="lastName"
										/>
									</div>
								</div>

								{#if user.role === 'PATIENT'}
									<div class="flex w-full flex-row items-start gap-2">
										<DatePicker.Root
											weekdayFormat="short"
											locale="en-GB"
											fixedWeeks={true}
											bind:value={date}
										>
											<div class="flex w-full max-w-[232px] flex-col gap-1.5">
												<DatePicker.Label class="block text-sm font-medium select-none"
													>Birth date</DatePicker.Label
												>
												<input type="hidden" name="birthDate" value={date.toString()} />
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
																			<DatePicker.GridRow class="mb-1 flex w-full justify-between">
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
									</div>
								{/if}
							{/if}
						{/if}
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
