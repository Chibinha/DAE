
<script setup>
import { ref, onMounted, defineProps, inject } from 'vue';
import EditProduct from './EditProduct.vue';
const props = defineProps({
    onlyDetails: {
        required: false
    },
    productId: {
        required: true
    }
});

//receive emit 'close' from EditPhysicalProduct
//emits('close');




const makerUsername = "maker1";
const axios = inject('axios');
const physicalProducts = ref([]);
const product = ref([]);
const productExists = ref(false);
const physicalProductExists = ref(false);
const showCreateModifyProduct = ref(false);
const showEditPhysicalProduct = ref(false);
const showCreatePhysicalProduct = ref(false);
const isCreating = ref(false);
const numberOfProducts = ref(1);

const selectedPhysicalProductId = ref(null);

const fetchPhysicalProducts = async () => {
    try {
        const response = await axios.get(`maker/${makerUsername}/physicalproducts/${props.productId}`);
        physicalProducts.value = response.data;
        physicalProductExists.value = true;
        // transform the timestamp to a human readable format
        physicalProducts.value.forEach(physicalProduct => {
            physicalProduct.stockTimestamp = physicalProduct.stockTimestamp.substring(0, 19).replace('T', ' ');

        });
    } catch (error) {
        physicalProductExists.value = false;
        console.error("Error fetching physical products:", error);
    }

    // Fetch the product
    try {
        const response = await axios.get(`products/${props.productId}`);
        product.value = response.data;
        productExists.value = true;
    } catch (error) {
        productExists.value = false;
        console.error("Error fetching product details:", error);
    }
};

// Fetch physical products only when the component is mounted and onlyDetails is true
onMounted(async () => {
    await fetchPhysicalProducts();
});


//emits('close');



const editProduct = () => {
    // Handle edit product logic
    showCreateModifyProduct.value = !showCreateModifyProduct.value;
    isCreating.value = false;
};

const editPhysicalProduct = (id) => {
    // Toggle visibility of EditPhysicalProduct component
    selectedPhysicalProductId.value = id;
    isCreating.value = false;
    showEditPhysicalProduct.value = !showEditPhysicalProduct.value;
};
const addMultiplePhysicalProducts = () => {
    const requestData = {
        productId: parseInt(props.productId),
        amount: parseInt(numberOfProducts.value)
    };

    axios.post(`maker/${makerUsername}/physicalproducts/list`, requestData)
        .then(response => {
            fetchPhysicalProducts();
        })
        .catch(error => {
            console.error('Error creating multiple physical products:', error);
        });
};



const handleClose = () => {
    console.log("handleClose");
    showCreateModifyProduct.value = false;
    showEditPhysicalProduct.value = false;
    showCreatePhysicalProduct.value = false;
    fetchPhysicalProducts();
};

const deletePhysicalProduct = (physicalProductId) => {
    axios.delete(`maker/${makerUsername}/physicalproducts/${physicalProductId}`)
        .then(response => {
            fetchPhysicalProducts();
        })
        .catch(error => {
            console.error('Error deleting physical product:', error);
        });
};
</script>

<template>
    <div v-if="!productExists">
        <p>Product not found.</p>
    </div>

    <div v-else>
        <br>
        <div v-if="!onlyDetails">
            <div class="col mx-auto text-center">
                <div class="card shadow-sm sm-4 mb-4">
                    <div class="card-header text-center">
                        <h4>{{ product.name }}: {{ product.id }}</h4>
                    </div>
                    <div class="card-body">
                        <h5>Description:</h5>
                        <p class="card-text">{{ product.description }}</p>
                        <h5>Ingredients:</h5>
                        <p class="card-text">{{ product.ingredients }}</p>
                        <h5>Weight</h5>
                        <p class="card-text">{{ product.weight }}</p>
                        <h5>Stock:</h5>
                        <p class="card-text"> {{ product.inStock }}</p>
                        <div class="text-center">
                            <h5> {{ product.price }}€</h5>
                        </div>
                        <div class="text-center">
                            <button class="btn btn-warning m-1 edit-button" @click="editProduct">Edit Product</button>
                        </div>
                    </div>
                </div>
                <EditProduct v-if="showCreateModifyProduct && !isCreating" class="col-md-6 mx-auto"
                    :makerUsername="makerUsername" :productId="product.id" :isCreating="false" @close="handleClose" />
            </div>

        </div>



        <div>

            <div v-if="physicalProducts.length !== 0">
                <h2>Stock items</h2>
                <div v-if="physicalProductExists">
                    <table class="table table-sm">
                        <thead>
                            <tr>
                                <th>Item Id</th>
                                <th>Product Name</th>
                                <th>Product Id</th>
                                <th>Added to stock</th>
                                <th v-if="!onlyDetails">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="physical in physicalProducts" :key="physical.id">
                                <td>{{ physical.id }}</td>
                                <td>{{ physical.productName }}</td>
                                <td>{{ physical.productId }}</td>
                                <td>{{ physical.stockTimestamp }}</td>
                                <td v-if="!onlyDetails">
                                    <button type="button" class="btn btn-danger"
                                        @click="deletePhysicalProduct(physical.id)">Delete item</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div v-else>
                <p>No stock items found.</p>
            </div>

            <div v-if="!onlyDetails">
                <div class="d-flex align-items-center m-1">
                    <input v-model="numberOfProducts" type="number" min="1" required />
                    <button class="btn btn-success ms-2 m-1" @click="addMultiplePhysicalProducts">Add {{ numberOfProducts}} items</button>
                </div>
            </div>
        </div>
    </div>
</template>
  