<template>
  <div>
    <div v-for="(message, index) in messages" :key="index">
      {{ message }}
    </div>
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount, onMounted } from 'vue';

const messages = ref([]);
let webSocket;

function connect() {
  webSocket = new WebSocket('ws://localhost:8080/academics/alerts/joao');
  console.log("Connected");

  webSocket.addEventListener('message', (event) => {
    const newMessage = event.data;
    messages.value = [...messages.value, newMessage]; // Update messages array
    console.log(newMessage);
  });

  webSocket.addEventListener('close', () => {
    console.log('Connection closed. Reconnecting...');
    setTimeout(() => connect(), 1000); // Reconnect after 1 second
  });
}

onMounted(() => {
  connect();
});

onBeforeUnmount(() => {
  webSocket.close();
});
</script>
