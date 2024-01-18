import { ref, computed, inject } from 'vue'
import { useUserStore } from './user.js'
import { defineStore } from 'pinia'

export const useOrderStore = defineStore('order', () => {
    const axios = inject('axios')
    const userStore = useUserStore()
    const orders = ref([])
    const order = ref([])
    const products = ref([])

    async function loadOrders() {
        try {
            const response = await axios.get(`client/${userStore.username}/orders`);
            orders.value = response.data;
            return orders.value;
        } catch (error) {
            console.error('Error loading orders:', error);
            clearOrders();
            throw error;
        }
    }

    async function loadOrder(id) {
        try {
            const response = await axios.get(`client/${userStore.username}/orders/` + id)
            order.value = response.data
            return order.value
        }
        catch (error) {
            throw error
        }
    }

    async function loadProducts(id) {
        try {
            const response = await axios.get(`client/${userStore.username}/orders/${id}/products`)
            products.value = response.data
            return order.value
        }
        catch (error) {
            throw error
        }
    }
    
    return {
        loadOrders,
        loadOrder,
        loadProducts,
        orders,
        order
    }
})