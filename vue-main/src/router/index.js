import { createRouter, createWebHistory } from 'vue-router';
// Routes
import { authRoutes } from '@/modules/auth/routes/auth.routes';
import { mainRoutes } from '@/modules/app/routes/app.routes';

let routes = [];

routes = routes.concat(authRoutes);
routes = routes.concat(mainRoutes);

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
