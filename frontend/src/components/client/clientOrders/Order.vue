<script setup>
import { useOrderStore } from '../../../stores/order'
// import ProductTable from './ProductTable.vue'
// import AlertTable from './AlertTable.vue'
// import { useProductStore } from '../../../stores/product'
// import { useUserStore } from '../../../stores/user'

import { ref, inject, computed, watch, onMounted } from 'vue'

const orderStore = useOrderStore()

const props = defineProps({
  id: {
    type: Number,
    default: null
  }
})

const loadOrder = async () => {
  try {
    await orderStore.loadOrder(props.id)
  } catch (error) {
    console.log(error)
  }
}

const loadProducts = async () => {
  try {
    await orderStore.loadProducts(props.id)
  } catch (error) {
    console.log(error)
  }
}

onMounted(() => {
    loadOrder()
    loadProducts()
})

</script>

<template> 

      <form class="row g-3" >
        <h3 class="mt-5 mb-3">Encomenda #{{ orderStore.order.id }}</h3>
        <hr>
        <table class="table">
            <thead>
            <tr>
                <th>Custo Total</th>
                <th>Estado</th>
                <th>Tipo</th>
                <th>Data de Encomenda</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
                <td>{{ orderStore.order.totalPrice }}€</td>
                <td>{{ orderStore.order.status }}</td>
                <td>{{ orderStore.order.type }}</td>
                <td>{{ orderStore.order.orderTimestamp }}</td>
            </tbody>
        </table>

        <h5 class="mt-5 mb-3">Produtos Encomendados:</h5>


        <table class="table">
            <thead>
            <tr>
                <th>Id Encomenda</th>
                <th>Custo Total</th>
                <th>Status</th>
                <th>Type</th>
                <th>Data de Encomenda</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="order in orders" :key="order.idOrder">
                <td>{{ order.id }}</td>
                <td>{{ order.totalPrice }}€</td>
                <td>{{ order.status }}</td>
                <td>{{ order.type }}</td>
                <td>{{ order.orderTimestamp }}</td>
                <td class="text-end">
                <div class="d-flex justify-content-end">
                    <button class="btn btn-xs btn-light" @click="seeDetails(order)"><i
                        class="bi bi-xs bi-info-circle"></i>
                    </button>
                </div>
                </td>
            </tr>
            </tbody>
        </table>

        <h5 class="mt-5 mb-3">Observações:</h5>

        <!-- <h5 v-if="totalAlertas == 0">Não tem Alertas </h5>
        <alerts-table v-else
            :alerts="getAlerts">
        </alerts-table> -->
    </form>
</template>

<style scoped></style>
