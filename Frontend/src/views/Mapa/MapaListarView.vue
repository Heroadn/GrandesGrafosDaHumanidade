<script setup lang="ts">
import MapaListar from '@/components/Mapa/MapaListar.vue' 

import '@mdi/font/css/materialdesignicons.css'

</script>

<template>
  <MapaListar />
</template>

<script lang="ts">
  //TODO: transformar em componente
  import { mapStores } from 'pinia'

  export default {
      computed:
      {
      },
      data: () => 
      {
        return {
          isLoaded: false,
          dialog: false
        };
      },
      async mounted() {
        this.isLoaded = true;
      },
      methods: 
      {
        atualizar()
        {
          this.isLoaded = false;
          this.isLoaded = true;
        }
      },
  };
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
