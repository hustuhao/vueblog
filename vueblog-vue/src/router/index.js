import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogSearch from '../views/BlogSearch.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/', //路由路径
    name: 'Index', //路由名称
    redirect: {name: "Blogs"} //重定向，等同于 component: Blogs
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/blogs/search',
    name: 'BlogSearch',
    component: BlogSearch
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/delete/:blogId',
    name: 'BlogDelete',
    meta: {
      requireAuth: true
    }, 
    component: Blogs,
  },
  {
    path: '/kaptcha',
    name: 'kaptcha',
    component: Blogs,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
