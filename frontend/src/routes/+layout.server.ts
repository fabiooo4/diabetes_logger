import type { PageServerLoad } from './$types';

export const load: PageServerLoad = ({ locals }: { locals: App.Locals }) => {
  return {
    user: locals.user
  };
};
