import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "../stores/user.js"

import HomeView from '../views/HomeView.vue'
import Login from "../components/auth/Login.vue"
import Cart from "../components/client/Cart.vue"
import Orders from "../components/orders/Orders.vue"
import Order from "../components/orders/Order.vue"
import EditOrder from "../components/orders/EditOrder.vue"
import ProductCRUD from "../components/productCRUD/productCRUD.vue"
import PhysicalProductCRUD from "../components/productCRUD/PhysicalProductCRUD.vue"
import Alerts from "../components/alerts/Alerts.vue"
import Products from "../components/manufacturer/Products.vue"
import ProductDetails from "../components/manufacturer/ProductDetails.vue"
import Catalog from "../components/client/Catalog.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },

    // CLIENT ENDPOINTS
    // catalog
    {
      path: '/catalog',
      name: 'catalog',
      component: Catalog,
      meta: { requiresAuth: true, roles: ['Customer'] }
    },
    {
      path: '/orders',
      name: 'orders',
      component: Orders,
      meta: { requiresAuth: true, roles: ['Customer', 'WarehouseOperator' ] }
    },
    {
      path: '/orders/:id',
      name: 'Order',
      component: Order,
      props: true,
      meta: { requiresAuth: true, roles: ['Customer', 'WarehouseOperator'] }
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart,
      meta: { requiresAuth: true, roles: ['Customer'] }
    },
    {
      path: '/orders/:id/edit',
      name: 'EditOrder',
      component: EditOrder,
      props: true,
      meta: { requiresAuth: true, roles: ['Customer'] }
    },

    // OTHERS
    {
      path: '/productCRUD',
      name: 'productCRUD',
      component: ProductCRUD,
      meta: { requiresAuth: true, roles: ['Manufacturer'] }
    },
    {
      path: '/physicalProductCRUD',
      name: 'physicalProductCRUD',
      component: PhysicalProductCRUD,
      meta: { requiresAuth: true, roles: ['Manufacturer'] }
    },
    {
      path: '/products',
      name: 'products',
      component: Products,
      meta: { requiresAuth: true, roles: ['Manufacturer', 'Customer'] }
    },
    {
      path: '/products/:productId',
      name: 'product-details',
      component: ProductDetails,
      props: true,
      meta: { requiresAuth: true, roles: ['Manufacturer', 'Customer'] }
    },
    {
      path: '/alerts',
      name: 'alerts',
      component: Alerts,
      meta: { requiresAuth: true, roles: ['Customer', 'WarehouseOperator', 'Manufacturer'] }
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
    await userStore.restoreToken()
  }

  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  // Customer WarehouseOperator Manufacturer

  // Check if userStore.user is not null before accessing 'role'
  const userRoles = userStore.user ? (
    Array.isArray(userStore.user.role)
      ? userStore.user.role
      : [userStore.user.role]
  ) : []

  if (requiresAuth && userRoles.length === 0) {
    // Redirect to login if the route requires authentication and user is not logged in
    next('/login')
  } else if (requiresAuth && !userRoles.some(role => to.meta.roles.includes(role))) {
    // Redirect to unauthorized page if the user does not have the required role
    alert('You are not authorized to access this page')
    next('/')
  } else {
    // Allow access
    next()
  }
})



export default router
