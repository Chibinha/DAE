<template>
    <div class="mt-3">
      <h1>
        <span>My Products</span>
      </h1>
      <button class="btn btn-primary" @click="createProduct">Create Product</button>
  
      <!-- Modify Product Modal -->
      
  
      <!-- Delete Product Modal -->
      
  
      <div>
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
          <tbody v-for="product in products" :key="product.id">
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
                <button class="btn btn-secondary" @click="toggleDetails(product.id)">More details</button>
                <button class="btn btn-secondary" @click="modifyProduct(product.id)">Modify Product</button>
                <button class="btn btn-danger" @click="deleteProduct(product.id)">Delete Product</button>
              </td>
            </tr>
  
            <!-- "More details" structure under -->
            <tr v-if="expandedProductId === product.id">
              <td :colspan="products.length + 1">
                <!-- Another nested table for physical product details -->
                <table class="table table-sm">
                  <thead>
                    <tr>
                      <th>Id</th>
                      <th>Product Id</th>
                      <th>Product Name</th>
                      <th>Serial Number</th>
                      <th>Stock Timestamp</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="physical in physicalProductsMap.get(product.id)" :key="physical.id">
                      <td>{{ physical.id }}</td>
                      <td>{{ physical.productId }}</td>
                      <td>{{ physical.productName }}</td>
                      <td>{{ physical.serialNumber }}</td>
                      <td>{{ physical.stockTimestamp }}</td>
                    </tr>
                  </tbody>
                </table>
              </td>
            </tr>
            <ProductsCreateModify v-if="showModifyProduct" :makerUsername="makerUsername" :productId="product.id"
        :isCreating="isCreating" @close="showCreateModifyProduct = false" />
            <!-- ... -->
          </tbody>
        </table>
      </div>
      <ProductsCreateModify v-if="showCreateProduct" :makerUsername="makerUsername" :productId="0"
        :isCreating="true" @close="showCreateProduct = false" />
    </div>
  </template>
  
  <script setup>
  import { ref, inject, onMounted } from 'vue';
  import ProductsCreateModify from './ProductsCreateModify.vue';
  
  const makerUsername = "maker1";
  const axios = inject('axios');
  const products = ref([]);
  const physicalProductsMap = ref(new Map());
  const expandedProductId = ref(null);
  const showCreateProduct = ref(false);
  const showModifyProduct = ref(false);
  const showDeleteProduct = ref(false);
  const selectedProductId = ref(null);
  const selectedDeleteProductId = ref(null);
  const isCreating = ref(false);
  
  const fetchProducts = async () => {
    try {
      const response = await axios.get(`maker/${makerUsername}/products`);
      products.value = response.data;
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  
    for (let i = 0; i < products.value.length; i++) {
      try {
        const response = await axios.get(`maker/${makerUsername}/physicalproducts/${products.value[i].id}`);
        physicalProductsMap.value.set(products.value[i].id, response.data);
      } catch (error) {
        console.error("Error fetching physical products:", error);
      }
    }
  };
  
  onMounted(async () => {
    await fetchProducts();
  });
  
  const toggleDetails = (productId) => {
    expandedProductId.value = (expandedProductId.value === productId) ? null : productId;
  };
  
  const createProduct = () => {
    isCreating.value = true;
    showCreateProduct.value = !showCreateProduct.value;
    selectedProductId.value = null;
  };
  
  const modifyProduct = (productId) => {
    isCreating.value = false;
    showModifyProduct.value = !showModifyProduct.value;
    selectedProductId.value = productId;
  };
  
  const deleteProduct = (productId) => {
    console.log("deleteProduct", productId);
  };
  
  </script>
  