<template>
    <div>
        <div v-if="product">
            <h2>Details of {{ product.name }}</h2>
            <div>
                <strong>ID:</strong> {{ product.id }}
            </div>
            <div>
                <strong>Name:</strong> {{ product.name }}
            </div>
            <div>
                <strong>Description:</strong> {{ product.description }}
            </div>
            <div>
                <strong>Weight:</strong> {{ product.weight }}
            </div>
            <div>
                <strong>Ingredients:</strong> {{ product.ingredients }}
            </div>
            <button @click="toggleModifySection">Modify</button>

            <!-- Collapsible section for modification -->
            <div v-if="showModifySection">
                <h3>Modify Product</h3>
                <div>
                    New Name: <input v-model="newName" type="text">
                </div>
                <div>
                    New Description: <input v-model="newDescription" type="text">
                </div>
                <div>
                    New Weight: <input v-model="newWeight" type="number">
                </div>
                <div>
                    New Ingredients: <input v-model="newIngredients" type="text">
                </div>
                <button @click="modifyProduct">Save Changes</button>
            </div>

            <button v-if="!showModifySection" @click="deleteProduct">Delete</button>
        </div>
        <h2>Error messages:</h2>
        {{ messages }}
    </div>
</template>
  
<script setup>
import { ref, reactive } from 'vue'

const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

async function fetchProduct() {
    const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`)
    return { product, productErr }
}

const { product, productErr } = await fetchProduct()
const messages = ref([])
const showModifySection = ref(false)
const newName = ref('')
const newDescription = ref('')
const newWeight = ref(0)
const newIngredients = ref('')

if (productErr.value) messages.value.push(productErr.value)

async function toggleModifySection() {
    showModifySection.value = !showModifySection.value
    // Reset input fields when collapsing the section
    if (!showModifySection.value) {
        resetModificationFields()
    }
}

function resetModificationFields() {
    newName.value = ''
    newDescription.value = ''
    newWeight.value = 0
    newIngredients.value = ''
}

async function modifyProduct() {
    const modificationObject = {}
    if (newName.value) modificationObject.name = newName.value
    if (newDescription.value) modificationObject.description = newDescription.value
    if (newWeight.value !== 0) modificationObject.weight = newWeight.value
    if (newIngredients.value) modificationObject.ingredients = newIngredients.value

    const requestOptions = {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(modificationObject)
    }

    const { error } = await useFetch(`${api}/products/${id}`, requestOptions)

    if (!error.value) {
        fetchProduct()
        toggleModifySection()
    }

    messages.value.push(error.value)
}

async function deleteProduct() {
    const requestOptions = {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    }

    const { error } = await useFetch(`${api}/products/${id}`, requestOptions)

    if (!error.value) {
        navigateTo('/products')        
    }
    messages.value.push(error.value)
}
</script>
  