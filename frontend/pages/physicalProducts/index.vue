<template>
    <div v-if="error">Error: {{ error.message }}</div>
    <div v-else>
        <nuxt-link to="/physicalProducts/create">Create a New Physical Products</nuxt-link>
        <h2>Physical Products</h2>
        <!-- public class PhysicalProductDTO {
    private Long id;
    private long productId;
    private String productName;
    private String serialNumber;
    private Timestamp stockTimestamp; -->
        <table>
            <tr>
                <th>Id</th>
                <th>Product Name</th>
                <th>Product Id</th>
                <th>Serial Number</th>
                <th>Stock Timestamp</th>
                <th>actions</th>
            </tr>
            
            <tr v-for="product in physicalProducts">
                <td>{{ product.id }}</td>
                <td>{{ product.productName }}</td>
                <td>{{ product.productId }}</td>
                <td>{{ product.serialNumber }}</td>
                <td>{{ product.stockTimestamp }}</td>

                <td>
                    <nuxt-link :to="`/physicalProducts/${product.id}`">Details</nuxt-link>
                </td>
            </tr>
        </table>
    </div>
    <button @click.prevent="refresh">Refresh Data</button>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: physicalProducts, error, refresh } = await useFetch(`${api}/physicalProducts`)
</script>