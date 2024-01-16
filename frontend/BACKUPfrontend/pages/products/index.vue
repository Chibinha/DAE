<template>
    <div v-if="error">Error: {{ error.message }}</div>
    <div v-else>
        <nuxt-link to="/products/create">Create a New Product</nuxt-link>
        <h2>Products</h2>
        <table>
            <tr>
                <th>Product ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Weight</th>
                <th>Ingredients</th>
                <th>actions</th>
            </tr>
            
            <tr v-for="product in products">
                <td>{{ product.id }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.description }}</td>
                <td>{{ product.weight }}</td>
                <td>{{ product.ingredients }}</td>
                <td>
                    <nuxt-link :to="`/products/${product.id}`">Details</nuxt-link>
                </td>
            </tr>
        </table>
    </div>
    <button @click.prevent="refresh">Refresh Data</button>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: products, error, refresh } = await useFetch(`${api}/products`)
</script>