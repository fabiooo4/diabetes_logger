// See https://svelte.dev/docs/kit/types#app.d.ts

import type { User } from "$lib/types";

// for information about these interfaces
declare global {
	namespace App {
    interface Locals {
      user: User | null;
    }
		// interface Error {}
		// interface PageData {}
		// interface PageState {}
		// interface Platform {}
	}
}
export {};
