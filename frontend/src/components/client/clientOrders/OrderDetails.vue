<script setup>
import { ref, watch, computed } from 'vue'
import { useUserStore } from '../../../stores/user'

const userStore = useUserStore()

const props = defineProps({
    transaction: {
        type: Object,
        required: true
    },
    operationType: {
        type: String,
        default: 'update'  // insert / update
    },
    errors: {
        type: Object,
        required: false,
    }
})


const transactionTitle = computed(() => {
    if (!editingTransaction.value) {
        return ''
    }
    return props.operationType == 'insert' ? 'New Transaction' : 'Transaction #' + editingTransaction.value.id
})

const save = () => {
    emit('save', editingTransaction.value)
}

const cancel = () => {
    emit('cancel', editingTransaction.value)
}

const isInsertOperation = ref(props.operationType == 'insert')

</script>

<template>
</template>


<style scoped></style>
