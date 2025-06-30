import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
   
   
    {
        path: "/",
        component: () => import('@/views/layout/index.vue'),
        children: [
            {
                path:'',
                component: () => import('@/views/permission/index.vue')
            },
            {
                path: "/role",
                component: () => import ('@/views/role/index.vue')
            },
            {
                path: "/user-role",
                component: () => import ('@/views/user-role/index.vue')
            },
            {
                path: "/user",
                component: () => import ('@/views/user/index.vue')
            },
            {
                path: "/video",
                component: () => import ('@/views/video/index.vue')
            },
            {
                path: "/setting",
                component: () => import ('@/views/setting/index.vue')
            },
            {
                path: "/type",
                component: () => import ('@/views/type/index.vue')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/login/index.vue')
    }
]
const router = createRouter({
     history: createWebHashHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const whiteList = ['/login'];
  const isLoggedIn = !!sessionStorage.getItem('user');

  if (whiteList.includes(to.path)) {
    next();
  } else if (isLoggedIn) {
    next();
  } else {
    next('/login');
  }
});
export default router;