<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user.js'
const router = useRouter()
const toast = inject('toast')

const credentials = ref({
    username: '',
    password: ''
})

const userStore = useUserStore()

const emit = defineEmits(['login'])

const login = async () => {
    try {
        await userStore.login(credentials.value)
        console.log('User store:', userStore.user)
        console.log('isAuthenticated:', await userStore.isAuthenticated())

        if (await userStore.isAuthenticated()) { //
            console.log('User ' + userStore.user.username + ' has entered the application.')
            toast.success('User ' + userStore.user.username + ' has entered the application.')
            emit('login')
            router.back()
        } else {
            credentials.value.password = ''
            toast.error('User credentials are invalid!')
        }
    } catch (error) {
        console.error("Login error:", error);
        // Handle other error scenarios if needed
    }
};

</script>


<template>
    <form class="row g-3 needs-validation" novalidate @submit.prevent="login">
        <h3 class="mt-5 mb-3">Login</h3>
        <hr>
        <div class="mb-3">
            <div class="mb-3">
                <label for="inputUsername" class="form-label">Username</label>
                <input type="text" class="form-control" id="inputUsername" required v-model="credentials.username">
            </div>
        </div>
        <div class="mb-3">
            <div class="mb-3">
                <label for="inputPassword" class="form-label">Password</label>
                <input type="password" class="form-control" id="inputPassword" required v-model="credentials.password">
            </div>
        </div>
        <div class="mb-3 d-flex justify-content-center">
            <button type="button" class="btn btn-primary px-5" @click="login">Login</button>
        </div>
    </form>
</template>

