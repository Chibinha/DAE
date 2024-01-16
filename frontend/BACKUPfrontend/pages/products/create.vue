<template>
    <form @submit.prevent="create">
        <div>
            Name:
            <input v-model="productForm.name" type="text">
        </div>
        <div>
            Description:
            <input v-model="productForm.description" type="text">
        </div>
        <div>
            Weight:
            <input v-model="productForm.weight" type="number">
        </div>
        <div>
            Ingredients:
            <input v-model="productForm.ingredients" type="text">
        </div>
        <button type="reset">RESET</button>
        <button type="submit">CREATE</button>
        <nuxt-link to="/products">Return</nuxt-link>
    </form>
    {{ message }}
</template>
  
<script setup>
import { ref, reactive } from 'vue'

const productForm = reactive({
    name: '',
    description: '',
    weight: 0,
    ingredients: ''
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL

async function create() {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(productForm)
    }

    const { error } = await useFetch(`${api}/products`, requestOptions)

    if (!error.value) {
        navigateTo('/products')
    }

    message.value = error.value
}
</script>
  