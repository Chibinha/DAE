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

onMounted(() => {
    loadOrder()
})

</script>

<template> 
      <form class="row g-3 needs-validation" novalidate @submit.prevent="save">
        <h3 class="mt-5 mb-3">Encomenda #{{ orderStore.orderId }}</h3>
        <hr>

        <div class="mb-3">
            <label for="inputName" class="form-label">Estado</label>
            <input type="text" class="form-control" id="inputName" required v-model="orderStore.status">
        </div>
        <div class="mb-3">
            <label for="inputName" class="form-label">Data</label>
            <input type="text" class="form-control" id="inputName" required v-model="orderStore.date">
        </div>

        <!-- <product-table
            :products="getProducts">
        </product-table> -->

        <div class="mb-3">
            <label for="inputName" class="form-label">Status</label>
            <input type="text" class="form-control" id="inputName" required v-model="orderStore.total">
        </div>

        <!-- <h5 v-if="totalAlertas == 0">Não tem Alertas </h5>
        <alerts-table v-else
            :alerts="getAlerts">
        </alerts-table> -->
    </form>
</template>

<style scoped></style>
