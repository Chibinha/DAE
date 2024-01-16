<template>
    <div>
      <div v-if="physicalProduct">
        <h2>Details of {{ physicalProduct.productName }}</h2>
        <div>
          <strong>ID:</strong> {{ physicalProduct.id }}
        </div>
        <div>
          <strong>Product Name:</strong> {{ physicalProduct.productName }}
        </div>
        <div>
          <strong>Product ID:</strong> {{ physicalProduct.productId }}
        </div>
        <div>
          <strong>Serial Number:</strong> {{ physicalProduct.serialNumber }}
        </div>
        <button @click="toggleModifySection">Modify</button>
  
        <!-- Collapsible section for modification -->
        <div v-if="showModifySection">
          <h3>Modify Physical Product</h3>
          <div>
            New Serial Number: <input v-model="newSerialNumber" type="text">
          </div>
          <button @click="modifyPhysicalProduct">Save Changes</button>
        </div>
  
        <button v-if="!showModifySection" @click="deletePhysicalProduct">Delete</button>
      </div>
      <h2>Error messages:</h2>
      {{ messages }}
    </div>
  </template>
  
  <script setup>
  import { ref, onBeforeMount } from 'vue'
  
  const route = useRoute()
  const id = route.params.id
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  
  async function fetchPhysicalProduct() {
    const { data: physicalProduct, error: physicalProductErr } = await useFetch(`${api}/physicalProducts/${id}`)
    return { physicalProduct, physicalProductErr }
  }
  
  const { physicalProduct, physicalProductErr } = await fetchPhysicalProduct()
  const messages = ref([])
  const showModifySection = ref(false)
  const newSerialNumber = ref('')
  
  if (physicalProductErr.value) messages.value.push(physicalProductErr.value)
  
  onBeforeMount(() => {
    resetModificationFields()
  })
  
  function toggleModifySection() {
    showModifySection.value = !showModifySection.value
    // Reset input fields when collapsing the section
    if (!showModifySection.value) {
      resetModificationFields()
    }
  }
  
  function resetModificationFields() {
    newSerialNumber.value = ''
  }
  
  async function modifyPhysicalProduct() {
    
  
    const requestOptions = {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: newSerialNumber.value
    }
  
    const { error } = await useFetch(`${api}/physicalProducts/${id}`, requestOptions)
  
    if (!error.value) {
      await fetchPhysicalProduct() // Fetch the updated physical product details
      toggleModifySection() // Collapse the modification section after modification
    }
  
    messages.value.push(error.value)
  }
  
  async function deletePhysicalProduct() {
    const requestOptions = {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' }
    }
  
    const { error } = await useFetch(`${api}/physicalProducts/${id}`, requestOptions)
  
    if (!error.value) {
      navigateTo('/physicalProducts')        
    }
    messages.value.push(error.value)
  }
  </script>
  