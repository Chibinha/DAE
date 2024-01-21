// alerts.js
import { ref, inject } from 'vue';
import { useUserStore } from './user.js';
import { defineStore } from 'pinia';

const ALERTS_STORAGE_KEY = 'alerts';

export const useAlertsStore = defineStore('alerts', () => {
    const axios = inject('axios');
    const userStore = useUserStore();
    const alerts = ref([]);

    // Load alerts from local storage on store initialization
    alerts.value = JSON.parse(localStorage.getItem(ALERTS_STORAGE_KEY)) || [];

    async function loadAlerts() {
        try {
            const response = await axios.get(`/alerts/${userStore.user.username}`);
            alerts.value = response.data;

            // Save alerts to local storage
            localStorage.setItem(ALERTS_STORAGE_KEY, JSON.stringify(alerts.value));

            return alerts.value;
        } catch (error) {
            console.error('Error loading alerts:', error);
            clearAlerts();
            throw error;
        }
    }

    function clearAlerts() {
        alerts.value = [];

        // Clear alerts from local storage
        localStorage.removeItem(ALERTS_STORAGE_KEY);
    }

    function addAlert(message) {
        alerts.value.push(message);

        // Save updated alerts to local storage
        localStorage.setItem(ALERTS_STORAGE_KEY, JSON.stringify(alerts.value));

        console.log('Alert added:', message);
    }

    return {
        loadAlerts,
        alerts,
        clearAlerts,
        addAlert,
    };
});
