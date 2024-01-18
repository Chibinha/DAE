<script setup>
import OrderTable from './OrderTable.vue'
import { useOrderStore } from '../../../stores/order'
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const orderStore = useOrderStore()


const loadOrders = async () => {
  try {
    await orderStore.loadOrders()
  } catch (error) {
    console.log(error)
  }
}

const seeDetails = (order) => {
  router.push({ name: 'Order', params: { id: order.id } })
}

onMounted(() => {
    loadOrders()    
})

</script>

<template>
  <div class="d-flex justify-content-between">
    <div class="mx-2">
      <h3 class="mt-4">Encomendas</h3>
    </div>
    <div class="mx-2 total-filtro">
      <h5 class="mt-4">Total: {{ orderStore.orders.length }}</h5>
    </div>
  </div>
  <hr>
  <div class="mb-3 d-flex justify-content-between flex-wrap">
        <div class="mx-2 mt-2">
    </div>
  </div>
  <h5 v-if="orderStore.orders == 0">Não tem encomendas </h5>
  <order-table v-else
   :orders= orderStore.orders
   @details="seeDetails">
  </order-table>
</template>


<style scoped>
.filter-div {
  min-width: 12rem;
}
.total-filtro {
  margin-top: 0.35rem;
}
.btn-addprj {
  margin-top: 1.85rem;
}
</style>