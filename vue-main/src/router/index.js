import { createRouter, createWebHistory } from 'vue-router';
// Routes
import { authRoutes } from '@/modules/auth/routes/auth.routes';

let routes = [];

routes = routes.concat(authRoutes);

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
