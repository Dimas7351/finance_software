export const authRoutes = [
  {
    path: '',
    name: 'PageGreetingsLayout',
    component: () => import('@/modules/auth/views/greetings/page-greetings-layout.vue'),
    children: [
      {
        path: '',
        name: 'PageGreetings',
        component: () => import('@/modules/auth/views/greetings/page-greetings.vue'),
      },
      {
        path: 'sign-up',
        name: 'PageSignUpLayout',
        component: () => import('@/modules/auth/views/sign-up/page-sign-up-layout.vue'),
        children: [
          {
            path: 'step-1',
            name: 'PageSignUpFirst',
            component: () => import('@/modules/auth/views/sign-up/page-sign-up-first.vue'),
          },
          {
            path: 'step-2',
            name: 'PageSignUpSecond',
            component: () => import('@/modules/auth/views/sign-up/page-sign-up-second.vue'),
          },
          {
            path: 'step-3',
            name: 'PageSignUpThird',
            component: () => import('@/modules/auth/views/sign-up/page-sign-up-third.vue'),
          },
        ],
      },
      {
        path: 'sign-in',
        name: 'PageLoginLayout',
        component: () => import('@/modules/auth/views/login/page-login-layout.vue'),
        children: [
          {
            path: 'password-recovery',
            name: 'PagePasswordRecovery',
            component: () => import('@/modules/auth/views/login/page-password-recovery.vue'),
          },
        ],
      },
    ],
  },
];
