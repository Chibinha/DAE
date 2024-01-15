<script setup>
import { ref, inject, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/user.js';
const userStore = useUserStore();

const axios = inject('axios');
const toast = inject('toast');

const searchTerm = ref('');
const products = ref([]);

const fetchProducts = async () => {
  console.log("fetchProducts");
  axios.get("products").then((response) => {
    console.log(response.data);
    products.value = response.data;
    console.log(products.value);
  })
    .catch((error) => {
      toast.error('Cannot load products: ' + error);
    })
}

const filteredproducts = computed(() => {
  const searchTermLower = searchTerm.value.toLowerCase();
  return products.value.filter(product => {
    return (
      product.name.toLowerCase().includes(searchTermLower) ||
      product.description.toLowerCase().includes(searchTermLower)
    );
  });
});

onMounted(async () => {
  await fetchProducts();
})
</script>

<template>
  <div>
    <div class="mt-3">
      <h1>Catalog</h1>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <div class="form-group">
          <div class="input-group">
            <input v-model="searchTerm" type="text" name="filterStr" id="inputfilterStr"
              placeholder="Search name or description" class="form-control">
            <div class="input-group-append">
              <button class="input-group-text bg-success text-white">Clear</button>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 my-3" v-show="searchTerm">
            <h3>Products with "{{ searchTerm }}" in name or description</h3>
          </div>
        </div>
        <br>
        <div class="row">
          
          <!-- Repete para cada livro (exemplo 1º livro): INICIO -->
          <div v-for="product in filteredproducts" :key="product.id" class="col-sm-4">
            <div class="card shadow-sm sm-4 mb-4">
              <div class="card-header text-center">
                <h4>{{ product.name }}</h4>
              </div>
              <div class="card-body">
                <h5>Description:</h5>
                <p class="card-text">{{ product.description }}</p>
                <h5>Ingredients:</h5>
                <p class="card-text">{{ product.ingredients }}</p>
                <h5>Weight</h5>
                <p class="card-text">{{ product.weight }}</p>
                <h5>Stock:</h5>
                <p class="card-text"> 2</p>
                <div class="text-center">
                  <h5> 10€</h5>
                </div>
                <!-- Apenas para clientes: INICIO-->
                <div v-show="userStore.user">
                  <hr>
                  <div class="text-center">
                    <button class="btn btn-primary">
                      Adicionar ao carrinho
                    </button>
                  </div>
                </div>
                <!-- Apenas para clientes: FIM -->
              </div>
            </div>
          </div>
          <!-- Repete para cada livro (exemplo 1º livro): FIM -->

        </div>
      </div>
    </div>
  </div>



  <!-- <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Documentation</template>

    Vue’s
    <a href="https://vuejs.org/" target="_blank" rel="noopener">official documentation</a>
    provides you with all information you need to get started.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Tooling</template>

    This project is served and bundled with
    <a href="https://vitejs.dev/guide/features.html" target="_blank" rel="noopener">Vite</a>. The
    recommended IDE setup is
    <a href="https://code.visualstudio.com/" target="_blank" rel="noopener">VSCode</a> +
    <a href="https://github.com/johnsoncodehk/volar" target="_blank" rel="noopener">Volar</a>. If
    you need to test your components and web pages, check out
    <a href="https://www.cypress.io/" target="_blank" rel="noopener">Cypress</a> and
    <a href="https://on.cypress.io/component" target="_blank" rel="noopener"
      >Cypress Component Testing</a
    >.

    <br />

    More instructions are available in <code>README.md</code>.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Ecosystem</template>

    Get official tools and libraries for your project:
    <a href="https://pinia.vuejs.org/" target="_blank" rel="noopener">Pinia</a>,
    <a href="https://router.vuejs.org/" target="_blank" rel="noopener">Vue Router</a>,
    <a href="https://test-utils.vuejs.org/" target="_blank" rel="noopener">Vue Test Utils</a>, and
    <a href="https://github.com/vuejs/devtools" target="_blank" rel="noopener">Vue Dev Tools</a>. If
    you need more resources, we suggest paying
    <a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">Awesome Vue</a>
    a visit.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Community</template>

    Got stuck? Ask your question on
    <a href="https://chat.vuejs.org" target="_blank" rel="noopener">Vue Land</a>, our official
    Discord server, or
    <a href="https://stackoverflow.com/questions/tagged/vue.js" target="_blank" rel="noopener"
      >StackOverflow</a
    >. You should also subscribe to
    <a href="https://news.vuejs.org" target="_blank" rel="noopener">our mailing list</a> and follow
    the official
    <a href="https://twitter.com/vuejs" target="_blank" rel="noopener">@vuejs</a>
    twitter account for latest news in the Vue world.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Support Vue</template>

    As an independent project, Vue relies on community backing for its sustainability. You can help
    us by
    <a href="https://vuejs.org/sponsor/" target="_blank" rel="noopener">becoming a sponsor</a>.
  </WelcomeItem> -->
</template>
