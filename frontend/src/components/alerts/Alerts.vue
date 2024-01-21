<template>
  <div>
    <div v-for="alert in alerts" :key="alert">
      {{ alert }}
    </div>
  </div>
</template>

<script setup>
import {ref, onBeforeUnmount, watch, onMounted} from 'vue';
import {useUserStore} from '@/stores/user';
import {useAlertsStore} from '@/stores/alert';

const userStore = useUserStore();
const alertsStore = useAlertsStore(userStore.axios);

let webSocket;
const alerts = ref([]);

function connect(username) {
  if (!username) {
    console.warn('User not logged in. WebSocket connection not established.');
    return;
  }

  try {
    webSocket = new WebSocket(`ws://localhost:8080/academics/alerts/${username}`);

    webSocket.addEventListener('open', () => {
      console.log('WebSocket connection opened');
    });

    webSocket.addEventListener('message', (event) => {
      const newAlert = event.data;
      // Check if the alert is already in the array
      if (!alerts.value.includes(newAlert)) {
        alerts.value.push(newAlert);
        alertsStore.addAlert(newAlert);
        console.log(newAlert);
      }
    });

    webSocket.addEventListener('close', (event) => {
      console.log(`WebSocket connection closed. Code: ${event.code}, Reason: ${event.reason}`);
      console.log('Reconnecting...');
      connect(username); // Reconnect immediately without a delay
    });
  } catch (error) {
    console.error('Error while creating WebSocket connection:', error);
  }
}

function updateWebSocket() {
  if (webSocket) {
    webSocket.close();
  }
  const username = userStore.user ? userStore.user.username : null;
  // Clear existing alerts when the user changes
  alertsStore.clearAlerts();
  alerts.value = [];  // Clear local alerts as well
  connect(username);
}

watch(() => userStore.user, () => {
  updateWebSocket();
});

onBeforeUnmount(() => {
  if (webSocket) {
    webSocket.close();
  }
});

// Call connect when the component is created and ensure it's called only once
let isConnected = false;
onMounted(() => {
  if (!isConnected) {
    const initialUsername = userStore.user ? userStore.user.username : null;
    connect(initialUsername);
    isConnected = true;
  }
});
</script>
