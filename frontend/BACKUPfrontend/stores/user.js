import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
    const axios = inject('axios')

    const token = ref(null)
    const user = ref(null)

    function logout() {
        token.value = null
        user.value = null
    }

    return { token, user, logout }
})