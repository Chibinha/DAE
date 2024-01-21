<script setup>
import { useRouter } from 'vue-router';
import { ref, inject, onMounted, computed, defineEmits } from 'vue';
import { useUserStore } from '@/stores/user.js';

const userStore = useUserStore();
const emit = defineEmits(["removeProduct", "changePrice", "addProductToCart", "removeProductFromCart"]);

const axios = inject('axios');
const toast = inject('toast');
const cart = ref([]);
const router = useRouter();

const searchTerm = ref('');
const products = ref([]);
const customer = ref({}); // Add a ref for customer

const fetchProducts = async () => {
  try {
    const response = await axios.get("products");
    products.value = response.data.map(product => ({
      ...product,
      quantity: 1
    }));
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
    const totalQuantity = existingItem.quantity + product.quantity;
    if (totalQuantity <= product.inStock) {
      existingItem.quantity += product.quantity;
    } else {
      toast.warning('Cannot add more products than available in stock.');
      return;
    }
  } else {
    if (product.quantity <= product.inStock) {
      cart.value.push({ product, quantity: product.quantity });
    } else {
      toast.warning('Cannot add more products than available in stock.');
      return;
    }
  }

  console.log("Updated Cart:", cart.value);
  emit("addProductToCart", { product, quantity: product.quantity });
}

const handleRemoveProductFromCart = (product) => {
  const index = cart.value.findIndex(item => item.product.id === product.id);
  if (index !== -1) {
    const cartItem = cart.value[index];
    if (cartItem.quantity > 1) {
      cartItem.quantity -= 1;
    } else {
      cart.value.splice(index, 1);
      if (cart.value.length === 0) {
        toast.info('Your cart is now empty.');
      }
    }
    console.log("Updated Cart:", cart.value);
    emit("removeProductFromCart", { product, quantity: cartItem.quantity });
  }
}

const cartQuantity = (productId) => {
  const cartItem = cart.value.find(item => item.product.id === productId);
  return cartItem ? cartItem.quantity : 0;
}

const totalPrice = computed(() => {
  return cart.value.reduce((total, cartItem) => {
    return total + cartItem.quantity * cartItem.product.price;
  }, 0);
});

const createOrder = async () => {
  try {
    const itemsInOrder = {};
    cart.value.forEach(cartItem => {
      itemsInOrder[cartItem.product.id] = cartItem.quantity;
    });

    const response = await axios.post(`customer/${userStore.user.username}/orders`, itemsInOrder, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    toast.success('Order created successfully!');

    cart.value = [];

  } catch (error) {
    console.error('Error creating order:', error);

    toast.error('Error creating order. Please try again.');
  }
};

onMounted(async () => {
  await fetchProducts();
})
</script>


<template>
  <div>
    <div class="row mt-3">
      <!-- Product Catalog Section -->
      <div :class="{ 'col-md-12': !cart.length, 'col-md-8': cart.length }">
        <div class="mt-3">
          <h1>Catalog</h1>
        </div>
        <div class="row mt-3">
          <div class="col-12">
            <div class="form-group">
              <div class="input-group">
                <input
                  v-model="searchTerm"
                  type="text"
                  name="filterStr"
                  id="inputfilterStr"
                  placeholder="Search name or description"
                  class="form-control"
                />
                <div class="input-group-append">
                  <button @click="clearSearch" class="input-group-text bg-success text-white">Clear</button>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12 my-3" v-show="searchTerm">
                <h3>Products with "{{ searchTerm }}" in name or description</h3>
              </div>
            </div>
            <br />
            <div class="row">
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
                    <div v-show="userStore.user">
                      <hr />
                      <div class="d-flex justify-content-between align-items-center">
                        <div>
                          <button
                            :disabled="product.quantity > product.inStock || product.quantity + cartQuantity(product.id) > product.inStock"
                            @click="handleAddProductToCart(product)"
                            class="btn btn-primary"
                          >
                            Add to Cart {{ product.quantity }}
                          </button>
                          <div v-if="product.quantity > product.inStock" class="text-danger text-center mt-2">
                            Sem stock disponível
                          </div>
                        </div>
                        <div class="botoesQuant">
                          <div class="m-2">
                            <button
                              type="button"
                              class="btn btn-outline-secondary botaoQuant"
                              :disabled="product.quantity > product.inStock"
                              @click="subtractCounter(product)"
                            >
                              <i class="bi-dash"></i>
                            </button>
                          </div>
                          <div class="m-2">
                            <button
                              type="button"
                              class="btn btn-outline-secondary botaoQuant"
                              :disabled="product.quantity > product.inStock"
                              @click="addToCounter(product)"
                            >
                              <i class="bi-plus-lg"></i>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Your Cart Section -->
      <div v-if="cart.length > 0" class="col-md-4">
        <div class="mt-3">
          <h2>Shopping Cart</h2>
        </div>
        <div class="cart-list" style="max-height: 400px; overflow-y: auto; border: 1px solid #ddd; border-radius: 8px; padding: 15px;">
          <div v-for="cartItem in cart" :key="cartItem.product.id" class="card mb-3">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <h5 class="card-title">{{ cartItem.product.name }}</h5>
                  <p class="card-text">Quantity: {{ cartItem.quantity }}</p>
                  <p class="card-text">Price: {{ cartItem.product.price }}€</p>
                </div>
                <div>
                  <button @click="handleRemoveProductFromCart(cartItem.product)" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="mt-3">
          <h4>Total Price: {{ totalPrice }}€</h4>
          <button @click="createOrder" class="btn btn-success">
            Create Order
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
  .cart-list {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    transition: box-shadow 0.3s ease;
  }

  .cart-list.cart-highlight {
    box-shadow: 0 0 15px rgba(0, 255, 0, 0.2); /* Change the color as needed */
  }

  .card {
    border: 1px solid #ddd;
    border-radius: 8px;
    margin-bottom: 15px;
    transition: box-shadow 0.3s ease;
  }

  .card:hover {
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  }
</style>
