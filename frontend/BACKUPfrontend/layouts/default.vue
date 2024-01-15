<script setup>
import { useUserStore } from "@/stores/user.js";
// const router = useRouter()
const userStore = useUserStore()

function logout() {
 userStore.logout()
//  router.push('/')
}
</script>


<template>
  <p>This is the layout content</p>
 <nuxt-link to="/auth-test">Test</nuxt-link>
 
 <nuxt-link to="/auth/login">Login</nuxt-link>
 
 <nuxt-link to="/students">Students</nuxt-link>
 
 <a href="#" @click.prevent="logout">Logout</a>
 <div>
  <main>
      <slot />
    </main>
 </div>
</template>