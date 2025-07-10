<script lang="ts">
	import type { Patient, Report, MedicChangeLog, Medic, User } from '$lib/types';
	import CaretDown from 'phosphor-svelte/lib/CaretDown';
	import { Accordion, Button, Checkbox, Dialog, Label, Separator } from 'bits-ui';
	import PencilSimple from 'phosphor-svelte/lib/PencilSimple';
	import Asterisk from 'phosphor-svelte/lib/Asterisk';
	import Check from 'phosphor-svelte/lib/Check';
	import Minus from 'phosphor-svelte/lib/Minus';
	import X from 'phosphor-svelte/lib/X';
	import ReportsCard from '../patient/ReportsCard.svelte';
	import Trash from 'phosphor-svelte/lib/Trash';
	import { enhance } from '$app/forms';

	let { patient, user, form }: { patient: Patient; user: User; form?: { error: string } } =
		$props();

	let hasTherapy = $state(patient.therapy !== null);

	let reports: Promise<Report[]> = $state(Promise.resolve([]));
	let lastModified: MedicChangeLog | null = $state(null);
	let referralMedic: Medic | null = $state(patient.referralMedic ? patient.referralMedic : null);
	let referralMedicId: number | null = $derived(referralMedic ? referralMedic.id : null);

	async function getReports() {
		fetch('/dashboard/medic', {
			method: 'POST',
			body: JSON.stringify({ patientId: patient.id }),
			headers: {
				'Content-Type': 'application/json'
			}
		})
			.then((response) => {
				if (!response.ok) {
					console.error('Failed to fetch patient reports' + response.statusText);
					return;
				}
				return response.json();
			})
			.then((data) => {
				reports = Promise.resolve(data);
			})
			.catch((error) => {
				console.error('Error fetching patient reports:', error);
			});
	}

	async function getLastModified() {
		fetch('/dashboard/medic', {
			method: 'PATCH',
			body: JSON.stringify({ patientId: patient.id }),
			headers: {
				'Content-Type': 'application/json'
			}
		})
			.then((response) => {
				if (!response.ok) {
					console.error('Failed to fetch last patient modification' + response.statusText);
					return;
				}
				return response.json();
			})
			.then((data: { lastModified: MedicChangeLog | null }) => {
				lastModified = data.lastModified;
			})
			.catch((error) => {
				console.error('Error fetching last patient modification:', error);
			});
	}
</script>

<div
	class="bg-background-alt shadow-card border-muted flex w-full flex-row items-center gap-8 rounded-xl border p-4"
>
	<Accordion.Root class="w-full" type="multiple">
		<Accordion.Item value="1" class="border-dark-10 group px-1.5">
			<Accordion.Header
				class="relative"
				onclick={() => {
					getReports();
					getLastModified();
				}}
			>
				<div class="absolute top-0 right-12 cursor-default">
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
									Edit patient
								</Dialog.Title>
								<Separator.Root class="bg-muted -mx-5 mt-5 mb-5 block h-px" />
								<Dialog.Description class="text-destructive font-bold">
									{form?.error}
								</Dialog.Description>
								<form action="?/editPatient" method="POST" use:enhance>
									<input type="hidden" name="patientId" value={patient.id} />
									<div class="flex flex-col items-start gap-1 pt-4 pb-4">
										<Label.Root class="text-sm font-medium">Risk factor</Label.Root>
										<div class="relative w-full">
											<input
												id="riskFactor"
												class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
												type="text"
												placeholder="Enter the risk factor"
												value={patient.riskFactor}
												name="riskFactor"
											/>
											<!-- Possible icon -->
											<!-- <LockKeyOpen class="text-dark/30 absolute top-[14px] right-4 size-[22px]" /> -->
										</div>

										<Label.Root class="text-sm font-medium">Previous patologies</Label.Root>
										<div class="relative w-full">
											<textarea
												id="previousPatologies"
												class="rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex h-[4.5rem] w-full resize-none items-center border px-2 py-3 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
												placeholder="Add any previous patologies the patient had"
												value={patient.previousPatologies}
												name="previousPatologies"
											></textarea>
										</div>

										<Label.Root class="text-sm font-medium">Medic notes</Label.Root>
										<div class="relative w-full">
											<textarea
												id="medicNotes"
												class="rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex h-[4.5rem] w-full resize-none items-center border px-2 py-3 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
												placeholder="Add any notes you want to leave for the patient"
												value={patient.medicNotes}
												name="medicNotes"
											></textarea>
										</div>

										<input
											type="hidden"
											id="referralMedicId"
											name="referralMedicId"
											bind:value={referralMedicId}
										/>
										<Label.Root class="text-sm font-medium">Referral medic</Label.Root>
										<div class="flex w-full flex-row items-center gap-x-2">
											<div class="relative w-full">
												<input
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													disabled
													placeholder="No referral medic assigned"
													value={referralMedic
														? `${referralMedic.firstName} ${referralMedic.lastName}`
														: ''}
												/>
											</div>
											<button
												type="button"
												onclick={() => (referralMedic = null)}
												class="hover:bg-dark-10 inline-flex size-8 items-center justify-center rounded-[7px] bg-transparent transition-all"
											>
												<Trash class="text-destructive size-[18px]" />
											</button>
											<Button.Root
												type="button"
												onclick={() => (referralMedic = user.medic)}
												class="rounded-input bg-dark text-background shadow-mini hover:bg-dark/95 h-input
                          inline-flex w-1/4 items-center justify-center px-[21px] text-[15px]
                          font-semibold active:scale-[0.98] active:transition-all"
											>
												Assign yourself
											</Button.Root>
										</div>

										<div class="my-3 flex items-center space-x-3">
											<Checkbox.Root
												id="therapy"
												aria-labelledby="therapy-label"
												class="border-muted bg-foreground data-[state=unchecked]:border-border-input data-[state=unchecked]:bg-background data-[state=unchecked]:hover:border-dark-40 peer inline-flex size-[25px] items-center justify-center rounded-md border transition-all duration-150 ease-in-out active:scale-[0.98]"
												name="therapy"
												bind:checked={hasTherapy}
											>
												{#snippet children({ checked, indeterminate })}
													<div class="text-background inline-flex items-center justify-center">
														{#if indeterminate}
															<Minus class="size-[15px]" weight="bold" />
														{:else if checked}
															<Check class="size-[15px]" weight="bold" />
														{/if}
													</div>
												{/snippet}
											</Checkbox.Root>
											<Label.Root
												id="therapy-label"
												for="therapy"
												class="text-sm leading-none font-medium peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
											>
												Assign therapy
											</Label.Root>
										</div>
										{#if hasTherapy}
											<Label.Root class="relative text-sm font-medium"
												>Medicine
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="medicine"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													type="text"
													placeholder="Enter the medicine name"
													value={patient.therapy && patient.therapy.medicine
														? patient.therapy.medicine
														: ''}
													name="medicine"
												/>
											</div>

											<Label.Root class="relative text-sm font-medium"
												>Amount
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="amount"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													type="number"
													placeholder="Enter the amount of medicine to take"
													value={patient.therapy && patient.therapy.amount
														? patient.therapy.amount
														: ''}
													name="amount"
												/>
											</div>

											<Label.Root class="relative text-sm font-medium"
												>Daily intake
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<input
													id="dailyIntake"
													class="h-input rounded-input border-border-input bg-background text-foreground focus-within:border-border-input-hover focus-within:shadow-date-field-focus hover:border-border-input-hover w-full items-center border px-2 py-3 text-sm tracking-[0.01em] ring-transparent transition-all select-none"
													type="number"
													placeholder="Enter the daily intake of medicine"
													value={patient.therapy && patient.therapy.dailyIntake
														? patient.therapy.dailyIntake
														: ''}
													name="dailyIntake"
												/>
											</div>

											<Label.Root class="relative text-sm font-medium"
												>Directions
												<Asterisk class="text-destructive absolute -top-0 -right-3 size-[13px]" />
											</Label.Root>
											<div class="relative w-full">
												<textarea
													id="directions"
													class="rounded-card-sm border-border-input bg-background placeholder:text-foreground-alt/50 hover:border-dark-40 focus-within:border-border-input-hover focus-within:shadow-date-field-focus inline-flex h-[4.5rem] w-full resize-none items-center border px-2 py-3 text-base ring-transparent transition-all focus:outline-hidden sm:text-sm"
													placeholder="Add any notes you want to leave for the patient"
													value={patient.therapy && patient.therapy.directions
														? patient.therapy.directions
														: ''}
													name="directions"
												></textarea>
											</div>
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
				<Accordion.Trigger
					class="flex w-full flex-1 items-center justify-between transition-all select-none [&[data-state=open]>span>svg]:rotate-180"
				>
					<div class="flex w-full flex-row items-center gap-x-8">
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Name:</h1>
							<p>
								{patient.firstName}
								{patient.lastName}
							</p>
						</div>
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Birth date:</h1>
							<p class="text-foreground">
								{new Date(patient.birthDate).toLocaleDateString()}
							</p>
						</div>
						{#if patient.referralMedic}
							<div class="flex flex-row gap-x-1">
								<h1 class="text-foreground-alt">Referral medic:</h1>
								<p>
									{patient.referralMedic.firstName}
									{patient.referralMedic.lastName}
								</p>
							</div>
						{/if}
						{#if patient.riskFactor}
							<div class="flex flex-row gap-x-1">
								<h1 class="text-foreground-alt gap-x-1">Risk Factor:</h1>
								<p>
									{patient.riskFactor}
								</p>
							</div>
						{/if}
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
					{#if patient.previousPatologies}
						<div>
							<h1 class="text-foreground-alt">Previous patologies:</h1>
							<p>
								{patient.previousPatologies}
							</p>
						</div>
					{/if}
					{#if patient.medicNotes}
						<div>
							<h1 class="text-foreground-alt">Medic notes:</h1>
							<p>
								{patient.medicNotes}
							</p>
						</div>
					{/if}
					{#if patient.therapy}
						<h1 class="mt-2 text-lg font-bold">Therapy</h1>
						<div class="flex flex-row flex-wrap items-center gap-x-8">
							<div>
								<div>
									<h1 class="text-foreground-alt">Medicine:</h1>
									<p>
										{patient.therapy.medicine}
									</p>
								</div>
							</div>
							<div>
								<div>
									<h1 class="text-foreground-alt">Amount:</h1>
									<p>
										{patient.therapy.amount}
									</p>
								</div>
							</div>
							<div>
								<div>
									<h1 class="text-foreground-alt">Daily intake:</h1>
									<p>
										{patient.therapy.dailyIntake}
									</p>
								</div>
							</div>
						</div>
						<div>
							<div>
								<h1 class="text-foreground-alt">Directions:</h1>
								<p>
									{patient.therapy.directions}
								</p>
							</div>
						</div>
					{/if}

					<div class="flex w-full flex-col gap-y-4">
						<h1 class="text-2xl font-bold">Reports</h1>
						<ReportsCard {reports} role="MEDIC" />
					</div>

					{#if lastModified != null}
						<p class="text-foreground-alt text-xs">
							Last modified by: {lastModified.medic.firstName}
							{lastModified.medic.lastName}, at {new Date(lastModified.timestamp).toLocaleString()}
							{lastModified.action}
						</p>
					{/if}
				</div>
			</Accordion.Content>
		</Accordion.Item>
	</Accordion.Root>
</div>
