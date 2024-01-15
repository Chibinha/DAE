<template>
    <div class="mt-3 ">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h1>Create</h1>

                    <form @submit.prevent="create">
                        <div>
                            Name:
                            <input v-model="productForm.name" type="text">
                        </div>
                        <div>
                            Price:
                            <input v-model="productForm.price" type="number">
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
                        <!-- <nuxt-link to="/products">Return</nuxt-link> -->
                    </form>
                </div>
                <div class="col-sm">
                    <h1>Find</h1>
                    <div>
                        Product ID: <input v-model="updateForm.id" type="number">
                        <button @click="find">Find Product</button>
                    </div>
                    <div v-if="foundProduct">
                        <p>Product Name: {{ foundProduct.name }}</p>
                        <p>Product Price: {{ foundProduct.price }}</p>
                        <p>Product Description: {{ foundProduct.description }}</p>
                        <p>Product Weight: {{ foundProduct.weight }}</p>
                        <p>Product Ingredients: {{ foundProduct.ingredients }}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h1>Modify</h1>
                    <!-- product id -->
                    <div>
                        Product ID: <input v-model="updateForm.id" type="number">
                    </div>
                    <div>
                        New Name: <input v-model="updateForm.name" type="text">
                    </div>
                    <div>
                        New Price: <input v-model="updateForm.price" type="number">
                    </div>
                    <div>
                        New Description: <input v-model="updateForm.description" type="text">
                    </div>
                    <div>
                        New Weight: <input v-model="updateForm.weight" type="number">
                    </div>
                    <div>
                        New Ingredients: <input v-model="updateForm.ingredients" type="text">
                    </div>
                    <button @click="update">Save Changes</button>
                </div>
                <div class="col-sm">
                    <h1>Delete</h1>
                    <div>
                        Product ID: <input v-model="updateForm.id" type="number">
                        <button @click="remove">Delete Product</button>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, inject } from 'vue'

const productForm = reactive({
    name: '',
    price: 0,
    description: '',
    weight: 0,
    ingredients: ''
})

const updateForm = reactive({
    id: '',
    name: '',
    price: 0,
    description: '',
    weight: 0,
    ingredients: ''
})

const foundProduct = ref(null)

const axios = inject('axios')
const toast = inject('toast')

// CRUD

// CREATE
async function create() {
    try {
        const response = await axios.post('products', productForm)
        console.log(response.data)
        toast.success('Product created successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product creation failed')
    }
}

// READ
async function read() {
    try {
        const response = await axios.get('products')
        console.log(response.data)
        toast.success('Product read successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product read failed')
    }
}

// FIND // form id field
async function find() {
    try {
        const response = await axios.get('products/' + updateForm.id)
        foundProduct.value = response.data
        console.log(response.data)
        toast.success('Product found successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product find failed')
    }
}

// UPDATE
async function update() {
    try {
        const response = await axios.put('products/' + updateForm.id, updateForm)
        console.log(response.data)
        toast.success('Product updated successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product update failed')
    }
}

// DELETE
async function remove() {
    try {
        const response = await axios.delete('products/' + updateForm.id)
        console.log(response.data)
        toast.success('Product deleted successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product delete failed')
    }
}

</script>