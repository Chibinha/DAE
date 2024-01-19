import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

export const useSensorStore = defineStore('sensor', () => {
    const axios = inject('axios')
    const sensors = ref([])

    async function loadSensors() {
        try {
            const response = await axios.get(`sensors/all`);
            sensors.value = response.data;
            return sensors.value;
        } catch (error) {
            console.error('Error loading sensors:', error);
            clearSensors();
            throw error;
        }
    }

    function getCurrentSensor(id) {
        for (var element of sensors.value) 
        {
            if (element.lastPackage === id) 
            {
                return element;
            }
        }
        return null; // Return null if no match is found
    }

    function clearSensors() {
        sensors.value = [];
    }

    return {
        loadSensors,
        sensors,
        getCurrentSensor
    }
})
