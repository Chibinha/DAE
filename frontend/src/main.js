//import './assets/main.css'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-icons/font/bootstrap-icons.css"
import "bootstrap"

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import axios from 'axios'
import Toaster from "@meforma/vue-toaster";

const app = createApp(App)


const serverBaseUrl = 'http://localhost:8080/academics'
app.provide('axios', axios.create({
    baseURL: serverBaseUrl + '/api',
    headers: {
      'Content-type': 'application/json',
    },
  }))
app.provide('serverBaseUrl', serverBaseUrl)  

app.use(Toaster, {
    // Global/Default options
    position: 'top',
    timeout: 2000,
    pauseOnHover: true
})

app.provide('toast', app.config.globalProperties.$toast);

app.use(createPinia())
app.use(router)

app.mount('#app')
