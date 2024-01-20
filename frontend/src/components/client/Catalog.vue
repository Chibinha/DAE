<script setup>
import { ref, inject, onMounted, computed, defineEmits } from 'vue';
import { useUserStore } from '@/stores/user.js';

const userStore = useUserStore();
const emit = defineEmits(["removeProduct", "changePrice", "addProductToCart"]);

const axios = inject('axios');
const toast = inject('toast');
const cart = ref([]);

const searchTerm = ref('');
const products = ref([]);

const fetchProducts = async () => {
  console.log("fetchProducts");
  try {
    const response = await axios.get("products");
    console.log(response.data);
    products.value = response.data.map(product => ({
      ...product,
      quantity: 1 // Add quantity property with a default value
    }));
    console.log(products.value);
  } catch (error) {
    toast.error('Cannot load products: ' + error);
  }
}

const filteredproducts = computed(() => {
  const searchTermLower = searchTerm.value.toLowerCase();
  return products.value.filter(product => {
    return (
      product.name.toLowerCase().includes(searchTermLower) ||
      product.description.toLowerCase().includes(searchTermLower)
    );
  });
});

const addToCounter = (product) => {
  if (product.quantity < product.inStock) {
    product.quantity += 1;
    emit("changePrice", product);
  } else {
    toast.warning('Cannot add more products than available in stock.');
  }
}

const subtractCounter = (product) => {
  if (product.quantity > 1) {
    product.quantity -= 1;
    emit("changePrice", product);
  }
}

const handleAddProductToCart = (product) => {
  const existingItem = cart.value.find(item => item.product.id === product.id);

  if (existingItem) {
    existingItem.quantity += product.quantity;
  } else {
    cart.value.push({ product, quantity: product.quantity });
  }

  console.log("Updated Cart:", cart.value); // Log the cart array to the console
  emit("addProductToCart", { product, quantity: product.quantity });
}


onMounted(async () => {
  await fetchProducts();
})
</script>

<template>
  <div>
    <div class="mt-3">
      <h1>Catalog</h1>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <div class="form-group">
          <div class="input-group">
            <input v-model="searchTerm" type="text" name="filterStr" id="inputfilterStr"
              placeholder="Search name or description" class="form-control">
            <div class="input-group-append">
              <button class="input-group-text bg-success text-white">Clear</button>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 my-3" v-show="searchTerm">
            <h3>Products with "{{ searchTerm }}" in name or description</h3>
          </div>
        </div>
        <br>
        <div class="row">
          <!-- Repete para cada produto (exemplo 1º livro): INICIO -->
          <div v-for="product in filteredproducts" :key="product.id" class="col-sm-4">
            <div class="card shadow-sm sm-4 mb-4">
              <div class="card-header text-center">
                <h4>{{ product.name }}</h4>
              </div>
              <div class="card-body">
                <h5>Description:</h5>
                <p class="card-text">{{ product.description }}</p>
                <h5>Ingredients:</h5>
                <p class="card-text">{{ product.ingredients }}</p>
                <h5>Weight</h5>
                <p class="card-text">{{ product.weight }}</p>
                <h5>Stock:</h5>
                <p class="card-text">{{ product.inStock }}</p>
                <div class="text-center">
                  <h5>{{ product.price }}€</h5>
                </div>
                <!-- Apenas para clientes: INICIO-->
                <div v-show="userStore.user">
                  <hr>
                  <div class="d-flex justify-content-between align-items-center">
                    <div>
                      <button :disabled="product.quantity > product.inStock" @click="handleAddProductToCart(product)"
                        class="btn btn-primary">
                        Add to Cart {{ product.quantity }}
                      </button>
                      <div v-if="product.quantity > product.inStock" class="text-danger text-center mt-2">Sem stock
                        disponível</div>
                    </div>
                    <div class="botoesQuant">
                      <div class="m-2">
                        <button type="button" class="btn btn-outline-secondary botaoQuant"
                          :disabled="product.quantity > product.inStock" @click="subtractCounter(product)">
                          <i class="bi-dash"></i>
                        </button>
                      </div>
                      <div class="m-2">
                        <button type="button" class="btn btn-outline-secondary botaoQuant"
                          :disabled="product.quantity > product.inStock" @click="addToCounter(product)">
                          <i class="bi-plus-lg"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Apenas para clientes: FIM -->
              </div>
            </div>
          </div>
          <!-- Repete para cada produto (exemplo 1º produto): FIM -->
        </div>
      </div>
    </div>
</div></template>
