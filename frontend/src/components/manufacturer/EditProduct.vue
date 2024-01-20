<script setup>
import { ref, onMounted, defineProps, defineEmits, inject } from 'vue';

// Import axios from your setup or import it as needed
const axios = inject('axios');

// Define props to receive makerUsername and productId
const { makerUsername, productId, isCreating } = defineProps(['makerUsername', 'productId', 'isCreating']);
const emits = defineEmits();
console.log(makerUsername);
console.log(productId);
console.log(isCreating);


// Data properties for the form
const productName = ref('');
const productPrice = ref(0);
const productDescription = ref('');
const productWeight = ref(0);
const productIngredients = ref('');

// Method to submit the form (create/modify product)
const submitForm = () => {
  const formData = {
    name: productName.value || null,
    price: productPrice.value,
    description: productDescription.value || null,
    weight: productWeight.value,
    ingredients: productIngredients.value || null,
  };

  if (isCreating) {
    // Creating a new product
    console.log('Creating a new product');
    axios.post(`manufacturer/${makerUsername}/products`, formData)
      .then(response => {
        console.log('Product created:', response.data);
        resetForm();
        emits('close');
      })
      .catch(error => {
        console.error('Error creating product:', error);
      });
  } else {
    // Modifying an existing product
    axios.put(`manufacturer/${makerUsername}/products/${productId}`, formData)
      .then(response => {
        resetForm();
        emits('close');
      })
      .catch(error => {
        console.error('Error modifying product:', error);
      });
  }
};

// Method to reset form fields
const resetForm = () => {
  productName.value = '';
  productPrice.value = 0;
  productDescription.value = '';
  productWeight.value = 0;
  productIngredients.value = '';
};

// You can add more methods and data properties as needed

// Lifecycle hook if you need to perform any action on component mount
onMounted(() => {
  // Add any logic needed when the component is mounted
});
</script>

<template>
  <div class="card">
    <h2>{{ isCreating ? 'Create Product' : 'Modify Product' }}</h2>
    <br>

    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label for="productName" class="form-label">Product Name:</label>
        <input v-model="productName" type="text" class="form-control" id="productName" />
      </div>

      <div class="mb-3">
        <label for="productPrice" class="form-label">Product Price:</label>
        <input v-model="productPrice" type="number" class="form-control" id="productPrice" />
      </div>

      <div class="mb-3">
        <label for="productDescription" class="form-label">Product Description:</label>
        <textarea v-model="productDescription" class="form-control" id="productDescription"></textarea>
      </div>

      <div class="mb-3">
        <label for="productWeight" class="form-label">Product Weight:</label>
        <input v-model="productWeight" type="number" class="form-control" id="productWeight" />
      </div>

      <div class="mb-3">
        <label for="productIngredients" class="form-label">Product Ingredients:</label>
        <input v-model="productIngredients" type="text" class="form-control" id="productIngredients" />
      </div>

      <button type="submit" class="btn btn-primary m-2">{{ isCreating ? 'Create' : 'Modify' }} Product</button>
      <button type="button" class="btn btn-danger" v-if="!isCreating" @click="deleteProduct">Delete Product</button>
    </form>
  </div>
</template>