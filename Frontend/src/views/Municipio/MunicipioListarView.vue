<script setup lang="ts">
import MunicipioListar from '@/components/Municipio/MunicipioListar.vue' 

import '@mdi/font/css/materialdesignicons.css'

</script>

<template>
  <MunicipioListar />
</template>

<script lang="ts">
  //TODO: transformar em componente
  import { mapStores } from 'pinia'
  import { useUsuarioService } from '@/stores/usuarioService'
  import { useUsuario } from '@/stores/usuarioStore'
  import { useAuthService } from '@/stores/authService'
  //TODO: criar component q alerta caso o usuario nao tenha logado

  export default {
      computed:
      {
        ...mapStores(useUsuarioService, useUsuario, useAuthService)
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
