export const mainRoutes = [
  {
    path: '/app/budget',
    name: 'PageMainLayout',
    component: () => import('@/modules/app/views/page-main-layout.vue'),
    children: [
      {
        path: '',
        name: 'PageBudget',
        component: () => import('@/modules/app/views/page-budget.vue'),
      },
      {
        path: 'cards',
        name: 'PageCards',
        component: () => import('@/modules/app/views/page-cards.vue'),
      },
      {
        path: 'goals',
        name: 'PageGoals',
        component: () => import('@/modules/app/views/page-goals.vue'),
      },
      {
        path: 'operations',
        name: 'PageOperations',
        component: () => import('@/modules/app/views/page-operations.vue'),
      },
      {
        path: 'settings',
        name: 'PageSettings',
        component: () => import('@/modules/app/views/page-settings.vue'),
      },
    ],
  },
  {},
];
