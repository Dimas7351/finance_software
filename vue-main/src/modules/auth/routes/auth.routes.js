export const authRoutes = [
  // Start Page
  {
    path: '/',
    name: 'PageGreetings',
    component: () => import('@/modules/auth/views/greetings/page-greetings-layout.vue'),
    children: [
      {
        path: 'sign-up',
        name: 'PageSignUpLayout',
        component: () => import('@/modules/auth/views/sign-up/page-sign-up-layout.vue')
      },
      {
        path: 'sign-in',
        name: 'PageLoginLayout',
        component: () => import('@/modules/auth/views/login/page-login-layout.vue'),
        children: [
          {
            path: 'password-recovery',
            name: 'PagePasswordRecovery',
            component: () => import('@/modules/auth/views/login/page-password-recovery.vue')
          },
        ],
      },
    ],
  },
];
