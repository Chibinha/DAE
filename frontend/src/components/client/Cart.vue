<script setup>
//#region consts and imports
import { ref, inject, onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { useUserStore } from "../../stores/user.js"

const userStore = useUserStore()
const router = useRouter()
const axios = inject('axios')
const toast = inject('toast');

const isCartEmpty = ref(true)
const isCartfinished = ref(false)
const priceOfProductsList = ref([])

const serverBaseUrl = inject('serverBaseUrl')
const emit = defineEmits(["removeProduct", "changePrice"]);

const counter = ref(1)
const product = ref([])
const totalPrice = ref(0)

//#endregion


</script>


<template>
    <div class="flex-container">
        <div class="flex-child_medium text-center transform">
            OLAAA
        </div>
        <div class="flex-child text-center transform">
            11€
        </div>
        <div class="flex-child_big text-center">
            <div>
                Quantidade : {{ counter }}
            </div>
            <div class="botoesQuant">
                <div class="m-2">
                    <button type="button" class="btn btn-outline-secondary botaoQuant" @click="subtractCounter"><i class="bi-dash"></i></button>
                </div>
                <div class="m-2">
                    <button type="button" class="btn btn-outline-secondary botaoQuant" @click="addToCounter"><i class="bi-plus-lg"></i></button>
                </div>
            </div>
        </div>
        <div class="flex-child">
            <button type="button" class="btn btn-danger remove" @click="removeProduct(product.id)">Remove Product</button>
        </div>
    </div>
</template>