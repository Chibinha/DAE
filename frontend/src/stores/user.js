import { ref, computed, inject } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
    const axios = inject('axios')
    const serverBaseUrl = inject('serverBaseUrl')

    const user = ref([
        {
            username: '',
            name: '',
            email: '',
            role: '',
        },
    ])

    async function loadUser() {
        try {
            const response = await axios.get('auth/user')
            user.value = response.data
        } catch (error) {
            clearUser()
            throw error
        }
    }


    function clearUser() {
        delete axios.defaults.headers.common.Authorization
        sessionStorage.removeItem('token')
        user.value = null
    }

    async function login(credentials) {
        try {
            console.log(credentials)
            const request = {
                username: credentials.username,
                password: credentials.password.trim(),
            };

            const response = await axios.post('auth/login', request);
            axios.defaults.headers.common.Authorization = "Bearer " + response.data;
            sessionStorage.setItem('token', response.data);
            await loadUser();
            return true;
        } catch (error) {
            clearUser();
            return false;
        }
    }

    //is authenticated
    async function isAuthenticated() {
        if (user.value) {
            return true
        }
        return false
    }

    async function logout() {
        try {
            clearUser()
            return true
        } catch (error) {
            return false
        }
    }


    async function restoreToken() {
        let storedToken = sessionStorage.getItem('token')
        if (storedToken) {
            axios.defaults.headers.common.Authorization = "Bearer " + storedToken
            await loadUser()
            return true
        }
        clearUser()
        return false
    }

    return {
        user,
        login,
        logout,
        loadUser,
        clearUser,
        restoreToken,
        isAuthenticated,
    }
})
