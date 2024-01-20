<template>
  <div class="mt-3">
    <h1 class="mx-auto">
      <span class="p-3">My Products</span>
      <button class="btn btn-primary" @click="createProduct">Create Product</button>
    </h1>
    <br>

    <div class="card">
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Weight</th>
            <th>Ingredients</th>
            <th>In stock</th>
            <th>Maker's Name</th>
            <th></th>
          </tr>
        </thead>
        <tbody v-for="product in products" :key="product.id" @click="toggleDetails(product.id)">
          <tr>
            <!-- Product details -->
            <td>{{ product.id }}</td>
            <td>{{ product.name }}</td>
            <td class="text-end pe-4">{{ product.price }}</td>
            <td>{{ product.description }}</td>
            <td>{{ product.weight }}</td>
            <td>{{ product.ingredients }}</td>
            <td>{{ product.inStock }}</td>
            <td>{{ product.makerName }}</td>
            <td class="text-end" style="width:9.5rem;">
              <router-link :to="{ name: 'product-details', params: { productId: product.id } }">
                <button class="btn btn-secondary">More details</button>
              </router-link>
            </td>
          </tr>

          <!-- "More details" structure under -->
          <tr v-if="expandedProductId === product.id">
            <td :colspan="products.length + 1">
              <ProductDetails :onlyDetails="true" :productId="product.id" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <EditProduct class="col-md-6 mx-auto" v-if="showCreateProduct" :makerUsername="makerUsername" :productId="0"
    :isCreating="true" @close="showCreateProduct = false" />
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'
import EditProduct from './EditProduct.vue'
import ProductDetails from './ProductDetails.vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore();
const axios = inject('axios');
const toast = inject('toast');
const products = ref([]);
const expandedProductId = ref(null);
const showCreateProduct = ref(false);

const fetchProducts = async () => {
  try {
    const response = await axios.get(`manufacturer/${userStore.user.username}/products`);
    products.value = response.data;
  } catch (error) {
    console.error("Error fetching products:", error);
    toast.error("Error fetching products!");
  }
};

onMounted(async () => {
  await userStore.restoreToken();
  await fetchProducts();
});

const toggleDetails = (productId) => {
  expandedProductId.value = (expandedProductId.value === productId) ? null : productId;
};

const createProduct = () => {
  showCreateProduct.value = !showCreateProduct.value;
};
</script>
