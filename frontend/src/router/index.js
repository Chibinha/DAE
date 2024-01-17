import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "../stores/user.js"

import HomeView from '../views/HomeView.vue'
import Login from "../components/auth/Login.vue"
import Cart from "../components/client/Cart.vue"
import MyOrders from "../components/client/MyOrders.vue"
import ProductCRUD from "../components/productCRUD/productCRUD.vue"
import PhysicalProductCRUD from "../components/productCRUD/PhysicalProductCRUD.vue"
import Alerts from "../components/alerts/Alerts.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/myorders',
      name: 'myorders',
      component: MyOrders
    },    
    {
      path: '/cart',
      name: 'cart',
      component: Cart
    },    
    {
      path: '/productCRUD',
      name: 'productCRUD',
      component: ProductCRUD
    },        
    {
      path: '/physicalProductCRUD',
      name: 'physicalProductCRUD',
      component: PhysicalProductCRUD
    },
    {
      path: '/alerts',
      name: 'alerts',
      component: Alerts
    },
    {
      path: '/login',
      name: 'login',
      component: Login 
    },    
  ]
})

let handlingFirstRoute = true

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  if (handlingFirstRoute) {
    handlingFirstRoute = false
    // await userStore.restoreToken()
  }
  next()
})

export default router
