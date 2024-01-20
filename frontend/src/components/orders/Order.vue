<script setup>
import { useUserStore } from '@/stores/user';
import { useOrderStore } from '../../stores/order'

import { onMounted } from 'vue'

const userStore = useUserStore()
const orderStore = useOrderStore()

const save = async () => {
    try {
        order.value = await orderStore.updateOrder(order.value)
        originalValueStr = JSON.stringify(order.value)
        toast.success('Order #' + order.value.id + ' was updated successfully.')
        router.back()
    } catch (error) {
        console.log(error)
        if (error.response.status == 422) {
        errors.value = error.response.data.errors
        toast.error('Order #' + order.value.id  + ' was not updated due to validation errors!')
        } else {
        toast.error('Order #' + order.value.id  + ' was not updated due to unknown server error!')
        }
    }
}  

const cancel = () => {
  originalValueStr = JSON.stringify(order.value)
  router.back()
}


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

const edit = () => {
  originalValueStr = JSON.stringify(editingOrder.value)
  router.back()
}

onMounted(async () => {
  await userStore.restoreToken();
  loadOrder()
  loadProducts()
  loadObservations()
})

const formatTimestamp = (timestamp) => {
  const date = new Date(timestamp);
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;
};
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
        <td>{{ formatTimestamp(orderStore.order.orderTimestamp) }}</td>
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
          <td>{{ formatTimestamp(observation.timestamp) }}</td>
          <td>O sensor {{ observation.sensorId }} que mede {{ observation.type }} registou um/uma {{ observation.type }}
            de {{ observation.value }}{{ observation.unit }} </td>
          <td class="text-end">
          </td>
        </tr>
      </tbody>
    </table>
  </form>
</template>

<style scoped></style>
