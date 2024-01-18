import { ref, computed, inject } from 'vue'
import { useUserStore } from './user.js'
import { defineStore } from 'pinia'

export const useOrderStore = defineStore('order', () => {
    const axios = inject('axios')
    const userStore = useUserStore()
    const orders = ref([])
    const order = ref([])
    const products = ref([])
    const userType = userStore.userType;

    async function loadOrders() {
        try {
            const response = await axios.get(`${userType}/${userStore.username}/orders`);
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
            const response = await axios.get(`${userType}/${userStore.username}/orders/` + id)
            order.value = response.data
            return order.value
        }
        catch (error) {
            throw error
        }
    }

    async function loadProducts(id) {
        try {
            const response = await axios.get(`${userType}/${userStore.username}/orders/${id}/products`);
            const productsData = response.data;
            const productQuantityMap = {}; //Cria mapa para obter a quantidade
    
            productsData.forEach(product => {
                const productId = product.id;

                if (productQuantityMap[productId]) {
                    productQuantityMap[productId].quantity++;
                    productQuantityMap[productId].subtotal = product.price * productQuantityMap[productId].quantity;
                } else {
                    // Se já estiver no mapa adiciona o produto com quantidade 1
                    product.quantity = 1;
                    productQuantityMap[productId] = product;
                    productQuantityMap[productId].subtotal = product.price;
                }
            });
            products.value = Object.values(productQuantityMap);
        } catch (error) {
            console.error('Error loading products:', error);
        }
    }
        
    return {
        loadOrders,
        loadOrder,
        loadProducts,
        orders,
        order,
        products,
    }
})