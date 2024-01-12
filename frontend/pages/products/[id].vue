<template>
    <div v-if="product">
        <h2>Details of {{ id }}</h2>
        {{ product }}
    </div>
    <h2>Error messages:</h2>
    {{ messages }}
</template>
  
<script setup>
const route = useRoute()

const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`)

const messages = ref([])
if (productErr.value) messages.value.push(productErr.value)

</script>