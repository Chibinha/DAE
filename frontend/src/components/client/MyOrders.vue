<script setup>

// import { ref, inject, onMounted, computed } from 'vue'
// import { useUserStore } from '@/stores/user.js'
// const userStore = useUserStore()

// const axios = inject('axios')
// const serverBaseUrl = inject("serverBaseUrl");
// const orders = ref([])
// var clientType = userStore.user.tipo.toUpperCase();

// //fetch for userStore.user.id - axios route compras/{id}
// const authToken = sessionStorage.getItem('token');

// const fetchOrders = async () => {
//     try {
        
//         if (!clientType == 'C') {
//             const response = await axios.get(`compras/${userStore.user.id}`, {
//                 headers: {
//                     Authorization: `Bearer ${authToken}`, // Replace with your actual authentication token
//                 },
//             });

//             orders.value = response.data.data;
//             orders.value.forEach(order => {
//                 console.log(order.estado);
//                 //   order.data = order.data_compra.split(' ')[0];  
//                 order.estado = order.estado == 'P' ? 'Pendente' : order.estado == 'C' ? 'Confirmada' : 'Anulada';
//                 console.log(order.estado);
//             });
//         } else {
//             const response = await axios.get(`compras`, {
//                 headers: {
//                     Authorization: `Bearer ${authToken}`, // Replace with your actual authentication token
//                 },
//             });
//             orders.value = response.data.data;
//         }

//         console.log(orders.value);
//     } catch (error) {
//         console.error("Error fetching orders:", error);
//     }
// };

// onMounted(async () => {
//     await fetchOrders()
// })



{/* <div class="mt-3">
        <h1>
            <span v-if="clientType == 'C'">Minhas Compras</span>
            <span v-else-if="clientType == 'F'">Compras Pendentes</span>
        </h1>
        <div>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th v-if="clientType == 'F'">Cliente</th>
                        <th v-else-if="clientType == 'C'">Estado</th>
                        <th class="text-end pe-4">Total</th>
                        <!-- Só inclui estes 2 <th> se "Compras Pendentes" -->
                        <th v-if="clientType == 'F'"></th>
                        <th v-if="clientType == 'F'"></th>
                        <!-- Só inclui os 2 <th> anteriores se "Compras Pendentes" -->
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    <!-- Repete para cada compra (exemplo 1º compra SEM DETALHE): INICIO -->
                    <tr v-for="order in orders" :key="order.id">
                        <td>{{ order.id }}</td>
                        <td>{{ order.data }}</td>
                        <td v-if="clientType == 'C'">{{ order.estado }}</td>
                        <td v-else-if="clientType == 'F'">{{ order.cliente_id }}</td>
                        <td class="text-end pe-4">{{ order.total }}</td>
                        <!-- Só inclui estes 2 <td> se "Compras Pendentes" -->
                        <td v-show="order.estado == 'Pendente'" class="text-end" style="width:7rem;">
                            <button class="btn btn-primary">Confirmar</button>
                        </td>
                        <td v-show="order.estado == 'Pendente'" class="text-end" style="width:6rem;">
                            <button class="btn btn-danger">Anular</button>
                        </td>
                        <!-- Só inclui os 2 <td> anteriores se "Compras Pendentes" -->
                        <td class="text-end" style="width:9.5rem;">
                            <button class="btn btn-secondary">Mais detalhes</button>
                        </td>
                    </tr>
                    <!-- . . . Zona onde será inserido o detalhe da compra (neste caso está vazio) . . . -->
                    <!-- Repete para cada compra (exemplo 1º compra SEM DETALHE): FIM -->

<!-- "Mais detalhes" structure under-->

                    <!-- . . . Zona onde será inserido o detalhe da compra . . . -->
                    <!-- DETALHE COMPRA ABERTO: INICIO -->
                    <tr>
                        <td></td>
                        <!-- Valor de colspan pode ser 3 (Minhas Compras) ou 5 (Compras Pendentes) -->
                        <td colspan="3">
                            <!-- INICIO - Detalhes de uma compra -->
                            <div>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Qtd.</th>
                                            <th>Livro</th>
                                            <th class="text-end">Preço Un.</th>
                                            <th class="text-end">Subtotal</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>2</td>
                                            <td>Nome do Livro 1</td>
                                            <td class="text-end">13.33 €</td>
                                            <td class="text-end">26.66 €</td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>Nome do Livro 2</td>
                                            <td class="text-end">15.18 €</td>
                                            <td class="text-end">45.54 €</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- FIM - Detalhes de uma compra -->
                        </td>
                        <td></td>
                    </tr>
                    <!-- DETALHE COMPRA ABERTO: FIM -->
                    <!-- Repete para cada compra (exemplo 2º compra COM DETALHE): FIM -->

                </tbody>
            </table>
        </div>
    </div> */}
</script>


<template>
    
</template>