<template>
    <div v-if="physicalProduct">
        <h2>Details of {{ id }}</h2>
        {{ physicalProduct }}
    </div>
    <h2>Error messages:</h2>
    {{ messages }}
</template>
  
<script setup>
const route = useRoute()

const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

const { data: physicalProduct, error: physicalProductErr } = await useFetch(`${api}/physicalProducts/${id}`)

const messages = ref([])
if (physicalProductErr.value) messages.value.push(physicalProductErr.value)

</script>