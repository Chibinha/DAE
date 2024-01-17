<script setup>

const props = defineProps({
  orders: {
    type: Array,
    default: () => [],
  },
})

const emit = defineEmits(['details'])

const seeDetails = (id) => {
  emit('details', id)
}

</script>

<template>
    <table class="table">
    <thead>
      <tr>
        <th>Id</th>
        <th>Custo Total</th>
        <th>Data de Encomenda</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="order in orders" :key="order.idOrder">
        <td>{{ order.idOrder }}</td>
        <td>{{ order.price }}</td>
        <td>{{ order.date }}</td>
        <td class="text-end">
          <div class="d-flex justify-content-end">
            <button class="btn btn-xs btn-light" @click="seeDetails(order.idOrder)"><i
                class="bi bi-xs bi-pencil"></i>
            </button>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</template>
<style scoped>
</style>