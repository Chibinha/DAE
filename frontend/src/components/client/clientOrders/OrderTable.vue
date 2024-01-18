<script setup>

const props = defineProps({
  orders: {
    type: Array,
    default: () => [],
  },
})

const emit = defineEmits(['details'])

const seeDetails = (order) => {
  emit('details', order)
}

</script>

<template>
    <table class="table">
    <thead>
      <tr>
        <th>Id Encomenda</th>
        <th>Custo Total</th>
        <th>Status</th>
        <th>Encomendado a:</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="order in orders" :key="order.idOrder">
        <td>{{ order.id }}</td>
        <td>{{ order.totalPrice }}€</td>
        <td>{{ order.status }}</td>
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
</template>
<style scoped>
</style>