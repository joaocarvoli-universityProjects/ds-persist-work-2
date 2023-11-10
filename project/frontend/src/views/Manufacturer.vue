<script setup lang="ts">

import {useManufacturerStore} from "../stores/manufacturer.ts";
import {ref} from "vue";
import { Manufacturer } from "../model/Manufacturer.ts";

const manufacturerStore = useManufacturerStore()
const manufacturerItems = ref<Manufacturer[]>([])

async function getAllManufacturers(){
  const result = await manufacturerStore.getAllManufacturers()
  console.log('Result before assignment:', result);
  manufacturerItems.value = result;
  console.log('manufacturerItems after assignment:', manufacturerItems.value);
}
</script>

<template>
<h1>Manufacturer</h1>
</template>

<style scoped>

</style>