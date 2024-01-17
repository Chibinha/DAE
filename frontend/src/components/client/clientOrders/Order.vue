<script setup>
// import TransactionDetail from './TransactionDetail.vue'

// import { useTransactionStore } from '../../stores/transaction'
// import { useCategoryStore } from '../../stores/category'
// import { useUserStore } from '../../stores/user'

// import { ref, inject, computed, watch, onMounted } from 'vue'
// import { useRouter, onBeforeRouteLeave } from 'vue-router'
// import { useToast } from 'vue-toast-notification'

// const transactionStore = useTransactionStore()
// const categoryStore = useCategoryStore()
// const userStore = useUserStore()

// const router = useRouter()
// const toast = useToast()
// const axios = inject('axios')


// const newTransaction = () => {
//   return {
//     id: null,
//     vcard: userStore.userType == 'A' ? null : userStore.userId,
//     value: null,
//     type: userStore.userType == 'A' ? 'C' : 'D',
//     payment_type: null,
//     payment_reference: null,
//     pair_vcard: null,
//     description: null,
//     category_id: null,
//     custom_options: null,
//     custom_data: null,
//     confirmation_code: null
//   }
// }

// const transaction = ref(newTransaction())
// const errors = ref(null)
// const confirmationLeaveDialog = ref(null)
// // String with the JSON representation after loading the category (new or edit)
// let originalValueStr = ''

// const loadTransaction = async (id) => {
//   originalValueStr = ''
//   errors.value = null
//   if (!id || id < 0) {
//     transaction.value = newTransaction()
//     originalValueStr = JSON.stringify(transaction.value)
//   } else {
//     try {
//       const response = await axios.get('transactions/' + id)
//       transaction.value = response.data.data
//       originalValueStr = JSON.stringify(transaction.value)
//     } catch (error) {
//       console.log(error)
//     }
//   }
// }

// const save = async () => {
//   errors.value = null
//   if (operation.value == 'insert') {
//     try {
//         transaction.value = await transactionStore.insertTransaction(transaction.value)
//         originalValueStr = JSON.stringify(transaction.value)
//         toast.success('Transaction #' + transaction.value.id + ' was created successfully.')
//         router.back()
//     } catch (error) {
//         console.log(error)
//         if (error.response.status == 422) {
//         errors.value = error.response.data.errors
//         toast.error('Transaction was not created due to validation errors!')
//         } else {
//         toast.error('Transaction was not created due to unknown server error!')
//         }
//     }
//   } else {
//     try {
//         transaction.value = await transactionStore.updateTransaction(transaction.value)
//         originalValueStr = JSON.stringify(transaction.value)
//         toast.success('Transaction #' + transaction.value.id + ' was updated successfully.')
//         router.back()
//     } catch (error) {
//         console.log(error)
//         if (error.response.status == 422) {
//         errors.value = error.response.data.errors
//         toast.error('Transaction #' + props.id + ' was not updated due to validation errors!')
//         } else {
//         toast.error('Transaction #' + props.id + ' was not updated due to unknown server error!')
//         }
//     }
//   }
// }  

// const cancel = () => {
//   originalValueStr = JSON.stringify(transaction.value)
//   router.back()
// }

// const props = defineProps({
//   id: {
//     type: Number,
//     default: null
//   }
// })

// const operation = computed(() => {
//   return (!props.id || props.id < 0) ? 'insert' : 'update'
// })


// watch(
//   () => props.id,
//   (newValue) => {
//     loadTransaction(newValue)
//   },
//   {
//     immediate: true
//   }
// )

// const categories = ref([])

// const loadCategories = async () => {
//     await categoryStore.loadCategories()
//     categories.value = categoryStore.getCategoriesByFilter()
// }

// onMounted(() => {
//     loadCategories()
// })


// let nextCallBack = null
// const leaveConfirmed = () => {
//   if (nextCallBack) {
//     nextCallBack()
//   }
// }

// onBeforeRouteLeave((to, from, next) => {
//   nextCallBack = null
//   let newValueStr = JSON.stringify(transaction.value)
//   if (originalValueStr != newValueStr) {
//     // Some value has changed - only leave after confirmation
//     nextCallBack = next
//     confirmationLeaveDialog.value.show()
//   } else {
//     // No value has changed, so we can leave the component without confirming
//     next()
//   }
//})
</script>

<template>
</template>

<style scoped></style>
