<template>
    <div class="mt-3 ">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h1>Create</h1>

                    <form @submit.prevent="create">
                        <div>
                            Product ID:
                            <input v-model="physicalProductForm.productId" type="number">
                        </div>
                        <div>
                            Serial Number:
                            <input v-model="physicalProductForm.serialNumber" type="text">
                        </div>
                        <button type="reset">RESET</button>
                        <button type="submit">CREATE</button>
                        <!-- <nuxt-link to="/physicalProducts">Return</nuxt-link> -->
                    </form>
                </div>
                <div class="col-sm">
                    <h1>Find</h1>
                    <div>
                        Physical Product ID: <input v-model="updateForm.id" type="number">
                        <button @click="find">Find Product</button>
                    </div>
                    <div v-if="foundProduct">
                        <p>Physical Product ID: {{ foundProduct.id }}</p>
                        <p>Product ID: {{ foundProduct.productId }}</p>
                        <p>Product Name: {{ foundProduct.productName }}</p>
                        <p>Serial Number: {{ foundProduct.serialNumber }}</p>
                        <p>Stock Timestamp: {{ foundProduct.stockTimestamp }}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <h1>Modify</h1>
                    <!-- product id -->
                    Physical Product ID: <input v-model="updateForm.id" type="number">
                    <div>
                        New Serial Number: <input v-model="updateForm.serialNumber" type="text">
                    </div>
                    <button @click="update">Save Changes</button>
                </div>
                <div class="col-sm">
                    <h1>Delete</h1>
                    <div>
                        Physical Product ID: <input v-model="updateForm.id" type="number">
                        <button @click="remove">Delete Product</button>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, inject } from 'vue'

const physicalProductForm = reactive({
    serialNumber: '',
    productId: ''
})

const updateForm = reactive({
    id: '',
    serialNumber: ''
})

const foundProduct = ref(null)

const axios = inject('axios')
const toast = inject('toast')

// CRUD

// CREATE
async function create() {
    try {
        const response = await axios.post('physicalProducts', JSON.stringify(physicalProductForm))
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
        const response = await axios.get('physicalProducts')
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
        const response = await axios.get('physicalProducts/' + updateForm.id)
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
        const response = await axios.put('physicalProducts/' + updateForm.id, updateForm.serialNumber)
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
        const response = await axios.delete('physicalProducts/' + updateForm.id)
        console.log(response.data)
        toast.success('Product deleted successfully')
    } catch (error) {
        console.log(error)
        toast.error('Product delete failed')
    }
}

</script>