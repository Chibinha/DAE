<script setup>
import { useUserStore } from '@/stores/user';
import { useOrderStore } from '../../stores/order'
import { useSensorStore } from '../../stores/sensor'
import { usePackageStore } from '../../stores/package'

import { ref, onMounted, computed } from 'vue'

const userStore = useUserStore()
const orderStore = useOrderStore()
const sensorStore = useSensorStore()
const packageStore = usePackageStore()
const currentPackage = ref([])
const currentSensor = ref([])

const emit = defineEmits(['save'])

const editingOrder = ref(orderStore.order.value)

const props = defineProps({
})

const loadPackages = async () => {
  try {
    await packageStore.loadPackages()
  } catch (error) {
    console.log(error)
  }
}



const loadSensors = async () => {
  try {
    await sensorStore.loadSensors();
  } catch (error) {
    console.log(error);
  }
};

onMounted(async () => {
  await userStore.restoreToken();
  loadPackages()
  loadSensors()

})

function convertPackageType(type)
{
    if(type == 1)
        return "Primária"
    if(type == 2)
        return "Secundária"
    if(type == 3)
        return "Terciária"

}



const save = () => {
    emit('save', editingOrder.value)
}


</script>

<template>
  <form class="row g-3">
    <h3 class="mt-5 mb-3">Editar Encomenda #{{ orderStore.order.id }}</h3>
    <h3 class="mt-5 mb-3">{{ orderStore.order.id }}</h3>
    <h3 class="mt-5 mb-3">{{ currentSensor }}</h3>
    <h3 class="mt-5 mb-3">{{ currentPackage }}</h3>

        <!--STATUS-->
        <div class="mb-3">
            <label for="inputStatus" class="form-label">Estado</label>
            <select class="form-select pe-2"
                 id="inputStatus" >
                <option> {{orderStore.order.status}}</option>
                <option>Preparado</option>
                <option>Enviado</option>
                <option>Em transporte</option>
                <option>Recebido</option>
            </select>
        </div>

        <!--PACKAGE-->
        <div class="mb-3">
            <label for="inputStatus" class="form-label" >Embalagem</label>
            <select class="form-select pe-2" id="input" :disabled="orderStore.order.status != 'Criada'" v-model="editingOrder.status">
                <option v-if="currentPackage != null"  selected>Selecione uma embalagem</option> 
                <option v-for="packageOption in packageStore.packages" :key="packageOption.id">{{ packageOption.material }} ({{ convertPackageType(packageOption.packageType) }})</option>
            </select>
        </div>

        <!--SENSOR-->
        <div class="mb-3">
            <label for="inputStatus" class="form-label">Sensor</label>
            <select class="form-select pe-2" id="input" :disabled="orderStore.order.status != 'Criada'" >
                <option v-if="currentPackage != null" selected> Selecione um sensor</option> 
                <option v-for="sensorOption in sensorStore.sensors" :key="sensorOption.id">{{ sensorOption.name }} ({{ sensorOption.type }})</option>
            </select>
        </div>


        <!--BUTTONS-->
        <div v-if="!props.readOnly" class="mb-3 d-flex justify-content-end"> 
            <button type="button" class="btn btn-primary px-5" @click="save">Save</button>
            <button type="button" class="btn btn-light px-5" @click="cancel">Cancel</button>
        </div>
  </form>
</template>

<style scoped></style>
