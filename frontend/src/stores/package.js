import { ref, inject } from 'vue'
import { useUserStore } from './user.js'
import { defineStore } from 'pinia'

export const usePackageStore = defineStore('package', () => {
    const axios = inject('axios')
    const userStore = useUserStore()
    const packages = ref([])

    async function loadPackages() {
        try {
            const response = await axios.get(`transportationpackage/all`);
            packages.value = response.data;
            return packages.value;
        } catch (error) {
            console.error('Error loading packages:', error);
            clearPackages();
            throw error;
        }
    }

    function getCurrentPackage(id) {
        for (var element of packages.value) 
        {
            if (element.orderId === id) 
            {
                console.log(element.orderId , id)
                return element;
            }
        }
        return null; // Return null if no match is found
    }

    function clearPackages() {
        packages.value = [];
    }

    return {
        loadPackages,
        packages,
        getCurrentPackage
    }
})
