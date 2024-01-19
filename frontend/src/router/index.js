import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "../stores/user.js"

import HomeView from '../views/HomeView.vue'
import Login from "../components/auth/Login.vue"
import Cart from "../components/client/Cart.vue"
import Orders from "../components/client/clientOrders/Orders.vue"
import Order from "../components/client/clientOrders/Order.vue"
import ProductCRUD from "../components/productCRUD/productCRUD.vue"
import PhysicalProductCRUD from "../components/productCRUD/PhysicalProductCRUD.vue"
import Alerts from "../components/alerts/Alerts.vue"
import MakerProducts from "../components/manufacturer/Products.vue"
import ProductDetails from "../components/manufacturer/ProductDetails.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },

    //CLIENT ENDPOINTS
    {
      path: '/client/orders',
      name: 'orders',
      component: Orders
    },    
    {
      path: '/client/orders/:id',
      name: 'Order',
      component: Order,
      props: true,
    }, 
    {
      path: '/cart',
      name: 'cart',
      component: Cart
    },   
    
    //OTHERS
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
      path: '/maker/products',
      name: 'maker/products',
      component: MakerProducts
    },
    {
      path: '/maker/products/:productId',
      name: 'product-details',
      component: ProductDetails,
      props: true,
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
