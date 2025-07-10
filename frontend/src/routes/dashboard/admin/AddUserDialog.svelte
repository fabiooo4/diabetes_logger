<script lang="ts">
	import {Dialog, Separator } from 'bits-ui';
	import Plus from 'phosphor-svelte/lib/Plus';
	import {
		CalendarDate,
		getLocalTimeZone,
		now,
		today,
		ZonedDateTime
	} from '@internationalized/date';
	import X from 'phosphor-svelte/lib/X';
	import { enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import UserForm from '../../register/UserForm.svelte';

	let { form }: { form?: { error: string } } = $props();

	let date: CalendarDate = $state(today(getLocalTimeZone()));
	let time: ZonedDateTime = $state(now(getLocalTimeZone()));

	let formOpened = $state(false);
</script>

<Dialog.Root bind:open={formOpened}>
	<Dialog.Trigger
		onclick={() => {
			date = today(getLocalTimeZone());
			time = now(getLocalTimeZone());

			if (form) {
				form = undefined;
			}
		}}
		class="rounded-input bg-dark text-background
	  shadow-mini hover:bg-dark/95 focus-visible:ring-foreground focus-visible:ring-offset-background inline-flex
	  h-12 items-center justify-center px-[11px] text-[15px] font-semibold whitespace-nowrap transition-colors focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-hidden active:scale-[0.98]"
	>
		<Plus class="size-6" />
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
				Create new user
			</Dialog.Title>
			<Separator.Root class="bg-muted -mx-5 mt-5 mb-6 block h-px" />
			<form
				method="POST"
				action="?/createUser"
				use:enhance={() => {
					return async ({ update }) => {
						await update();

						if (form?.error === undefined) {
              invalidateAll();
							formOpened = false;
						}
					};
				}}
				class="flex h-full w-full flex-col items-center justify-center"
			>
				<UserForm {form} submitMessage="Save"/>
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
