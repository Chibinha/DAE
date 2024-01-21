<script setup>
import { useUserStore } from '@/stores/user';
import { useOrderStore } from '../../stores/order'

import { onMounted } from 'vue'

const userStore = useUserStore()
const orderStore = useOrderStore()

const props = defineProps({
  id: {
    type: String,
    default: null
  },
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

const loadObservations = async () => {
  try {
    await orderStore.loadObservations(props.id)
  } catch (error) {
    console.log(error)
  }
}

const seeProduct = (product) => {
  router.push({ name: 'Product', params: { id: product.id } })
}

onMounted(async () => {
  await userStore.restoreToken();
  loadOrder()
  loadProducts()
  loadObservations()
})

</script>

<template>
  <form class="row g-3">
    <div class="d-flex justify-content-between align-items-end">
    <h3 class="mt-5 mb-3">Encomenda #{{ orderStore.order.id }}</h3>
    <router-link v-show="userStore.user?.role == 'WarehouseOperator'" :class="{ active: $route.name === 'EditOrder' }" :to="{ name: 'EditOrder', props: { order: orderStore.order } } ">
        <button class="btn btn-primary align-self-end">
            Editar Encomenda
        </button>
    </router-link>
</div>
    <hr>
    <table class="table">
      <thead>
        <tr>
          <th>Encomendado a:</th>
          <th>Custo Total</th>
          <th>Estado</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <td>{{ orderStore.order.orderTimestamp }}</td>
        <td>{{ orderStore.order.totalPrice }}€</td>
        <td>{{ orderStore.order.status }}</td>
      </tbody>
    </table>

    <h5 class="mt-5 mb-3">Produtos Encomendados:</h5>
    <table class="table">
      <thead>
        <tr>
          <th>Produto</th>
          <th>Fabricante</th>
          <th>Descrição</th>
          <th>Preço Un.</th>
          <th>Quantidade</th>
          <th>Subtotal</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in orderStore.products" :key="product.id">
          <td>{{ product.name }}</td>
          <td>{{ product.makerName }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.price }}€</td>
          <td>{{ product.quantity }}</td>
          <td>{{ product.subtotal }}€</td>
          <td class="text-end">
          </td>
          <td class="text-end">
            <div class="d-flex justify-content-end">
              <button class="btn btn-xs btn-light" @click="seeProduct(product)"><i>Mais info</i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <h5 class="mt-5 mb-3">Observações:</h5>
    <table class="table">
      <thead>
        <tr>
          <th>Data</th>
          <th>Observação</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="observation in orderStore.observations" :key="observation.id">
          <td>{{ observation.timestamp }}</td>
          <td>O sensor {{ observation.sensorName }} que mede {{ observation.type }} registou um/uma {{ observation.type }}
            de {{ observation.value }}{{ observation.unit }} </td>
          <td class="text-end">
          </td>
        </tr>
      </tbody>
    </table>
  </form>
</template>

<style scoped></style>
