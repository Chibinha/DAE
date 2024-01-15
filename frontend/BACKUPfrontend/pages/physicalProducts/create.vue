<template>
    <form @submit.prevent="create">
      <div>
        Product ID:
        <input v-model="physicalProductForm.productId" type="number">
      </div>
      <div>
        Serial Number:
        <input v-model="physicalProductForm.serialNumber" type="text">
      </div>
      <button type="reset">RESET</button>
      <button type="submit">CREATE</button>
      <nuxt-link to="/physicalProducts">Return</nuxt-link>
    </form>
    {{ message }}
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  
  const physicalProductForm = reactive({
    productId: 0,
    serialNumber: ''
  })
  const message = ref('')
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  
  async function create() {
    const requestData = {
      productId: physicalProductForm.productId,
      serialNumber: physicalProductForm.serialNumber,
      stockTimestamp: new Date().toISOString()  // Assuming stockTimestamp is in ISO format
    }
  
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestData)
    }
  
    const { error } = await useFetch(`${api}/physicalProducts`, requestOptions)
  
    if (!error.value) {
      // Assuming there's a function navigateTo in your code to navigate to the home page
      navigateTo('/physicalProducts')
    }
  
    message.value = error.value
  }
  </script>
  