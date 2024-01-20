<template>
    <div>
      <h2>Shopping Cart</h2>
      <ul>
        <li v-for="item in cart" :key="item.product.id">
          {{ item.product.name }} - Quantity: {{ item.quantity }}
          <button @click="removeFromCart(item.product)">Remove</button>
        </li>
      </ul>
      <div v-if="cart.length === 0">
        <p>Your cart is empty.</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, defineEmits } from 'vue';
  
  const { cart } = defineProps(['cart']);
  
  const emit = defineEmits(["removeProductFromCart"]);
  
  const removeFromCart = (product) => {
    emit("removeProductFromCart", product);
  }
  </script>
  
  <style scoped>
  
  </style>