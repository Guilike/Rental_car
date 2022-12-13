import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '飞翔租车'}
    }]
  },

  {
    path: '/info',
    component: Layout,
    redirect: '/info/customer',
    name: 'Info',
    meta: {title:'基本信息',role:['0','1']},
    children: [
      {
        path: 'customer',
        name: 'Customer',
        component: () => import('@/views/customer/index'),
        meta: { title: '客户信息'}
      },
      {
        path: 'cars',
        name: 'Cars',
        component: () => import('@/views/cars/index'),
        meta: { title: '车辆信息'}
      }

    ] 
  },

  {
    path: '/business',
    component: Layout,
    redirect: '/business/customer',
    name: 'business',
    meta: {title:'业务管理',role:['0','1']},
    children: [
      {
        path: 'orders',
        name: 'Orders',
        component: () => import('@/views/orders/index'),
        meta: { title: '订单列表'}
      },
      {
        path: 'check',
        name: 'Check',
        component: () => import('@/views/check/index'),
        meta: { title: '入库检查'}
      }

    ] 
  },

  {
    path: '/rental',
    component: Layout,
    redirect: '/rental/choose',
    name: 'Rental',
    meta: {title:'开始租车',role:['2']},
    children: [
      {
        path: 'choose',
        name: 'Choose',
        component: () => import('@/views/choose/index'),
        meta: { title: '我要租车'}
      },
      {
        path: 'indent',
        name: 'Indent',
        component: () => import('@/views/indent/index'),
        meta: { title: '租车订单'}
      }

    ] 
  },

  {
    path: '/manage',
    component: Layout,
    meta: {role:['0']},
    children: [
      {
        path: 'index',
        name: 'Mmanage',
        component: () => import('@/views/manage/index'),
        meta: { title: '账号管理'}
      }
    ]
  },

  {
    path: '/person',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Person',
        component: () => import('@/views/person/index'),
        meta: { title: '个人信息'}
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]




const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
