import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import LoginPage from '@/pages/LoginPage.vue';
import RegisterPage from '@/pages/RegisterPage.vue';
import DashboardPage from '@/pages/DashboardPage.vue';
import LeadsPage from '@/pages/LeadsPage.vue';
import PipelinePage from '@/pages/PipelinePage.vue';
import ConversationsPage from '@/pages/ConversationsPage.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterPage
  },
  {
    path: '/',
    name: 'dashboard',
    component: DashboardPage,
    meta: { requiresAuth: true }
  },
  {
    path: '/leads',
    name: 'leads',
    component: LeadsPage,
    meta: { requiresAuth: true }
  },
  {
    path: '/pipeline',
    name: 'pipeline',
    component: PipelinePage,
    meta: { requiresAuth: true }
  },
  {
    path: '/conversations',
    name: 'conversations',
    component: ConversationsPage,
    meta: { requiresAuth: true }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore();
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'login' });
  } else if ((to.name === 'login' || to.name === 'register') && auth.isAuthenticated) {
    next({ name: 'dashboard' });
  } else {
    next();
  }
});

export default router;

