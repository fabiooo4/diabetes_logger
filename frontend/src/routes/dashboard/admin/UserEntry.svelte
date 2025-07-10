<script lang="ts">
	import type { User } from '$lib/types';
	import CaretDown from 'phosphor-svelte/lib/CaretDown';
	import { Accordion, Dialog, Separator } from 'bits-ui';
	import PencilSimple from 'phosphor-svelte/lib/PencilSimple';
	import X from 'phosphor-svelte/lib/X';
	import { enhance } from '$app/forms';
	import UserForm from '../../register/UserForm.svelte';
	import Trash from 'phosphor-svelte/lib/Trash';

	let { user, form }: { user: User; form?: { error: string } } = $props();
</script>

<div
	class="bg-background-alt shadow-card border-muted flex w-full flex-row items-center gap-8 rounded-xl border p-4"
>
	<Accordion.Root class="w-full" type="multiple">
		<Accordion.Item value="1" class="border-dark-10 group px-1.5">
			<Accordion.Header class="relative">
				<div class="absolute top-0 right-12 cursor-default">
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
										Delete user
									</Dialog.Title>
									<Separator.Root class="bg-muted -mx-5 mt-5 mb-6 block h-px" />
									<Dialog.Description class="text-foreground-alt mb-6 text-sm">
										Are you sure you want to delete this user? This action cannot be undone.
									</Dialog.Description>
									<div class="flex w-full justify-end">
										<form method="POST" action="?/deleteUser">
											<input type="hidden" value={user.id} name="userId" />
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
									Edit user
								</Dialog.Title>
								<Separator.Root class="bg-muted -mx-5 mt-5 mb-5 block h-px" />
								<form
									action="?/editUser"
									method="POST"
									use:enhance
									class="flex h-full w-full flex-col items-center justify-center"
								>
                  <input type="hidden" name="userId" value={user.id} />
                  <input type="hidden" name="verified" value={user.verified} />
									<UserForm {form} submitMessage="Save" {user} />
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
							<h1 class="text-foreground-alt">Role:</h1>
							<p>
								{user.role}
							</p>
						</div>
						<div class="flex flex-row gap-x-1">
							<h1 class="text-foreground-alt">Email:</h1>
							<p>
								{user.email}
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
					{#if user.role === 'PATIENT'}
						<div>
							<h1 class="text-foreground-alt">First name:</h1>
							<p>
								{user.patient.firstName}
							</p>
						</div>
						<div>
							<h1 class="text-foreground-alt">Last name:</h1>
							<p>
								{user.patient.lastName}
							</p>
						</div>
						<div>
							<h1 class="text-foreground-alt">Birth date:</h1>
							<p>
								{new Date(user.patient.birthDate).toLocaleDateString()}
							</p>
						</div>
					{:else if user.role === 'MEDIC'}
						<div>
							<h1 class="text-foreground-alt">First name:</h1>
							<p>
								{user.medic.firstName}
							</p>
						</div>
						<div>
							<h1 class="text-foreground-alt">Last name:</h1>
							<p>
								{user.medic.lastName}
							</p>
						</div>
					{/if}
				</div>
			</Accordion.Content>
		</Accordion.Item>
	</Accordion.Root>
</div>
