<script setup>
import { ref, onMounted, defineProps, inject } from 'vue';
import EditProduct from './EditProduct.vue';
import { useUserStore } from '@/stores/user';

const props = defineProps({
    onlyDetails: {
        required: false
    },
    productId: {
        required: true
    }
});

const userStore = useUserStore();
const axios = inject('axios');
const physicalProducts = ref([]);
const productDetails = ref([]);
const hasProductDetails = ref(false);
const hasPhysicalProducts = ref(false);
const isEditingProduct = ref(false);
const isEditingPhysicalProduct = ref(false);
const isCreatingPhysicalProduct = ref(false);
const isCreatingProduct = ref(false);
const quantityOfProducts = ref(1);
const availableProductPackages = ref([]);
const selectedProductPackages = ref([]);
const selectedProductPackageIds = ref([]);

const selectedPhysicalProductId = ref(null);

const fetchPhysicalProducts = async () => {
    try {
        const response = await axios.get(`manufacturer/${userStore.user.username}/items/${props.productId}`);
        physicalProducts.value = response.data;
        console.log("physicalProducts:", physicalProducts.value);
        physicalProducts.value.forEach(physicalProduct => {
            physicalProduct.stockTimestamp = formatTimestamp(physicalProduct.stockTimestamp);
        });
        hasPhysicalProducts.value = true;
        
    } catch (error) {
        hasPhysicalProducts.value = false;
        console.error("Error fetching physical products:", error);
    }

    // Fetch the product details
    try {
        const response = await axios.get(`products/${props.productId}`);
        productDetails.value = response.data;
        hasProductDetails.value = true;
    } catch (error) {
        hasProductDetails.value = false;
        console.error("Error fetching product details:", error);
    }
};

onMounted(async () => {
    await userStore.restoreToken();
    await fetchPhysicalProducts();
    await fetchAvailableProductPackages();
});

const editProduct = () => {
    isEditingProduct.value = !isEditingProduct.value;
    isCreatingProduct.value = false;
};

const editPhysicalProduct = (id) => {
    selectedPhysicalProductId.value = id;
    isCreatingProduct.value = false;
    isEditingPhysicalProduct.value = !isEditingPhysicalProduct.value;
};

const addMultiplePhysicalProducts = () => {
    const packageIds = selectedProductPackageIds.value.map(id => Number(id))
    console.log("packageIds:", packageIds);
    axios.post(`manufacturer/${userStore.user.username}/items/${props.productId}/${quantityOfProducts.value}`, packageIds)
        .then(response => {
            console.log("response:", response);
            fetchPhysicalProducts();
        })
        .catch(error => {
            console.error('Error adding physical products:', error);
        });
};


const handleClose = () => {
    isEditingProduct.value = false;
    isEditingPhysicalProduct.value = false;
    isCreatingPhysicalProduct.value = false;
    fetchPhysicalProducts();
};

const deletePhysicalProduct = (physicalProductId) => {
    axios.delete(`manufacturer/${userStore.user.username}/items/${physicalProductId}`)
        .then(response => {
            fetchPhysicalProducts();
        })
        .catch(error => {
            console.error('Error deleting physical product:', error);
        });
};

const fetchAvailableProductPackages = async () => {
    try {
        const response = await axios.get(`manufacturer/${userStore.user.username}/packages`);
        availableProductPackages.value = response.data;
        console.log("availableProductPackages:", availableProductPackages.value);
    } catch (error) {
        console.error("Error fetching available product packages:", error);
    }
};

const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}.${date.getMilliseconds().toString().padStart(3, '0')}`;
    return formattedDate;
};

const addProductPackage = () => {
    const selectedPackageIds = Array.isArray(selectedProductPackages.value)
        ? selectedProductPackages.value
        : [selectedProductPackages.value];

    selectedPackageIds.forEach((selectedPackageId) => {
        if (!selectedProductPackageIds.value.includes(selectedPackageId)) {
            selectedProductPackageIds.value.push(selectedPackageId);
            console.log("selectedProductPackageIds:", selectedProductPackageIds.value);
        }
    });
};

const removeProductPackage = (index) => {
    selectedProductPackageIds.value.splice(index, 1);
    console.log("selectedProductPackageIds:", selectedProductPackageIds.value);
};

const getProductPackageName = (packageId) => {
    const selectedPackage = availableProductPackages.value.find(pkg => pkg.id === packageId);
    return selectedPackage ? `${selectedPackage.type} : ${selectedPackage.material}` : '';
};


</script>

<template>
    <div v-if="!hasProductDetails">
        <p>Product not found.</p>
    </div>

    <div v-else>
        <br />
        <div v-if="!onlyDetails">
            <div class="col mx-auto text-center">
                <div class="card shadow-sm sm-4 mb-4">
                    <div class="card-header text-center">
                        <h4>{{ productDetails.name }}: {{ productDetails.id }}</h4>
                    </div>
                    <div class="card-body">
                        <h5>Description:</h5>
                        <p class="card-text">{{ productDetails.description }}</p>
                        <h5>Ingredients:</h5>
                        <p class="card-text">{{ productDetails.ingredients }}</p>
                        <h5>Weight</h5>
                        <p class="card-text">{{ productDetails.weight }}</p>
                        <h5>Stock:</h5>
                        <p class="card-text"> {{ productDetails.inStock }}</p>
                        <div class="text-center">
                            <h5> {{ productDetails.price }}€</h5>
                        </div>
                        <div class="text-center">
                            <button class="btn btn-warning m-1 edit-button" @click="editProduct">Edit Product</button>
                        </div>
                    </div>
                </div>
                <EditProduct v-if="isEditingProduct && !isCreatingProduct" class="col-md-6 mx-auto"
                    :makerUsername="userStore.user.username" :productId="productDetails.id" :isCreating="false"
                    @close="handleClose" />
            </div>
        </div>

        <div>
            <div v-if="physicalProducts.length !== 0">
                <h2>Stock items</h2>
                <div v-if="hasPhysicalProducts">
                    <table class="table table-sm">
                        <thead>
                            <tr>
                                <th>Item Id</th>
                                <th>Product Name</th>
                                <th>Product Id</th>
                                <th>Added to stock</th>
                                <th>Product Packages</th>
                                <th v-if="!onlyDetails">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="physical in physicalProducts" :key="physical.id">
                                <td>{{ physical.id }}</td>
                                <td>{{ physical.productName }}</td>
                                <td>{{ physical.productId }}</td>
                                <td>{{ physical.stockTimestamp }}</td>
                                <td>
                                    <div v-for="pkg in physical.productPackages" :key="pkg.id">
                                        <p>{{ pkg.type }} : {{ pkg.material }}</p>
                                    </div>
                                </td>
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

            <div class="text-center" v-if="!onlyDetails">
                <h2>Add Items</h2>


                <!-- Dynamic list of select boxes for product packages -->


                <div>
                    <select v-model="selectedProductPackages" class="form-select">
                        <option v-for="pkg in availableProductPackages" :key="pkg.id" :value="pkg.id">[{{ pkg.id }}] {{
                            pkg.type }} : {{ pkg.material }}</option>
                    </select>
                    <button class="btn btn-info ms-2 m-1" @click="addProductPackage">Add Package</button>

                </div>

                <!-- Display selected product packages -->
                <div v-if="selectedProductPackageIds.length > 0">
                    <h2>Selected Product Packages</h2>
                    <div v-for="(packageId, index) in selectedProductPackageIds" :key="index">
                        <p>{{ getProductPackageName(packageId) }}</p>
                        <button class="btn btn-danger" @click="removeProductPackage(index)">Remove Package</button>
                    </div>
                </div>

                <div class=" align-items-center m-1">
                    <input v-model="quantityOfProducts" type="number" min="1" required />
                    <button class="btn btn-success ms-2 m-1" @click="addMultiplePhysicalProducts">
                        Add {{ quantityOfProducts }} items
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
