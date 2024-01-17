import { ref, computed, inject } from 'vue'
import { useUserStore } from './user.js'
import { defineStore } from 'pinia'

export const useOrderStore = defineStore('order', () => {
    const axios = inject('axios')
    const userStore = useUserStore()
    const orders = ref([])

    async function loadOrders() {
        try {
            const response = await axios.get(`client/${userStore.username}/orders`)
            orders.value = response.data.data
            return orders.value
        }
        catch (error) {
            clearOrders()
            throw error
        }
    }

    const totalOrders = computed(() => {
        return orders.value.length
    })

    function clearOrders() {
        orders.value = []
    }

    return {
        loadOrders,
        totalOrders,
    }
})