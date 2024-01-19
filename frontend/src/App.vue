<script setup>
import { useRouter, RouterLink, RouterView,  } from "vue-router"
import { ref, inject, onBeforeMount } from 'vue'
import { useUserStore } from "./stores/user.js"

const router = useRouter()
const toast = inject("toast")
const userStore = useUserStore()

const buttonSidebarExpand = ref(null)

const logout = async () => {
  if (await userStore.logout()) {
    clickMenuOption()
    toast.success("User has logged out of the application.")
    router.push({ name: 'home' })

    console.log("User has logged out of the application.")
  } else {
    toast.error("There was a problem logging out of the application!")
    console.error("There was a problem logging out of the application!")
  }
}

const clickMenuOption = () => {
  if (window.getComputedStyle(buttonSidebarExpand.value).display !== "none") {
    buttonSidebarExpand.value.click()
  }
}

onBeforeMount(async () => {
  await userStore.restoreToken();
})
</script>

<template>
  <nav class="navbar navbar-expand-md navbar-dark bg-dark sticky-top flex-md-nowrap p-0 shadow">
    <div class="container-fluid">
      <router-link class="navbar-brand col-md-3 col-lg-2 me-0 px-3" :to="{ name: 'home' }" @click="clickMenuOption">
        <img src="@/assets/logo.svg" alt="" width="30" height="24" class="d-inline-block align-text-top" />
        DAE
      </router-link>
      <router-link v-show="userStore.user" class="nav-link" :class="{ active: $route.name === 'alerts' }" :to="{ name: 'alerts' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                <button class="btn btn-primary">
                  Alerts
                </button>
              </router-link>
      <button id="buttonSidebarExpandId" ref="buttonSidebarExpand" class="navbar-toggler" type="button"
        data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse justify-content-end">
        <ul class="navbar-nav">
          <li class="nav-item" v-show="!userStore.user">
            <router-link class="nav-link" :class="{ active: $route.name === 'login' }" :to="{ name: 'login' }"
              @click="clickMenuOption">
              <i class="bi bi-box-arrow-in-right"></i>
              Login
            </router-link>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
              data-bs-toggle="dropdown" aria-expanded="false">
              <img src="@/assets/avatar-none.png" class="rounded-circle z-depth-0 avatar-img" alt="avatar image"/>
              <span class="avatar-text">{{ userStore.user?.name ?? "Anonymous" }}</span>
            </a>
            <ul v-show="userStore.user" class="dropdown-menu dropdown-menu-dark dropdown-menu-end" aria-labelledby="navbarDropdownMenuLink">
              <li>
                <a class="dropdown-item" @click.prevent="logout">
                  <i class="bi bi-arrow-right"></i>Logout
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container-fluid">
    <div class="row">
      <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
        <div class="position-sticky pt-3">
          <ul class="nav flex-column">
            <li v-show="!userStore.user || userStore.user?.role == 'Client'" class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'home' }" :to="{ name: 'home' }"
                @click="clickMenuOption">
                <i class="bi bi-house"></i>
                Catalog
              </router-link>
            </li>

            <div v-show="userStore.user">
            <li v-show="userStore.user?.role == 'Client' " class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'orders' }" :to="{ name: 'orders' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                My Orders
              </router-link>
            </li>

            <li v-show="userStore.user?.role == 'Client'" class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'cart' }" :to="{ name: 'cart' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                Cart
              </router-link>
            </li>

            <li v-show="userStore.user?.role == 'Admin'" class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'productCRUD' }" :to="{ name: 'productCRUD' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                productCRUD
              </router-link>
            </li>

            <li v-show="userStore.user?.role == 'Admin'" class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'physicalProductCRUD' }" :to="{ name: 'physicalProductCRUD' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                physicalProductCRUD
              </router-link>
            </li>

              

            <li v-show="userStore.user?.role == 'Maker'" class="nav-item">
              <router-link class="nav-link" :class="{ active: $route.name === 'maker/products' }" :to="{ name: 'maker/products' }"
                @click="clickMenuOption">
                <i class="bi bi-circle"></i>
                maker/products
              </router-link>
            </li>
          </div>

          </ul>

          <div class="d-block d-md-none">
            <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
              <span>User</span>
            </h6>
            <ul class="nav flex-column mb-2">
              <li class="nav-item" v-show="!userStore.user">
                <router-link class="nav-link" :class="{ active: $route.name === 'login' }" :to="{ name: 'login' }"
                  @click="clickMenuOption">
                  <i class="bi bi-box-arrow-in-right"></i>
                  Login
                </router-link>
              </li>
              <li class="nav-item dropdown" v-show="userStore.user">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink2" role="button"
                  data-bs-toggle="dropdown" aria-expanded="false">
                  <img src="@/assets/avatar-none.png" class="rounded-circle z-depth-0 avatar-img" alt="avatar image"/>
                  <span class="avatar-text">{{ userStore.user?.name ?? "Anonymous" }}</span>
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink2">
                  <li>
                    <a class="dropdown-item" @click.prevent="logout">
                      <i class="bi bi-arrow-right"></i>Logout
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<style>
@import "./assets/dashboard.css";

.avatar-img {
  margin: -1.2rem 0.8rem -2rem 0.8rem;
  width: 3.3rem;
  height: 3.3rem;
}

.avatar-text {
  line-height: 2.2rem;
  margin: 1rem 0.5rem -2rem 0;
  padding-top: 1rem;
}

.dropdown-item {
  font-size: 0.875rem;
}

.btn:focus {
  outline: none;
  box-shadow: none;
}

#sidebarMenu {
  overflow-y: auto;
}
</style>