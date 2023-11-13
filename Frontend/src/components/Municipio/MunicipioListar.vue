<script setup lang="ts">
import MunicipioItem from '@/components/Municipio/MunicipioItem.vue' 
import MunicipioPesquisar from './MunicipioPesquisar.vue';
import MapaListar from '@/components/Mapa/MapaListar.vue'
</script>

<template>
  <v-card 
      class="mx-auto">
    <v-container v-if="isLoading" class="pa-1" style="max-height: 100vh;">
      <MunicipioPesquisar @searchResults="update" :municipios="municipios"/>
      
      <perfect-scrollbar>
        <v-item-group multiple>
          <v-row>
            <v-col v-for="municipio, i in municipiosSearch"
              :key="i"
              cols="12"
              md="4">
              <MunicipioItem 
                :municipio="municipio"/>
            </v-col>
          </v-row>
        </v-item-group>
      </perfect-scrollbar>
    </v-container>

    
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { useMunicipio, Municipio } from '@/stores/municipioService'
 

  export default {
      computed:
      {
      },
      data: () =>  
      {
        return {
          municipios: [] as Municipio[],
          municipiosSearch: [] as Array<Municipio>,
          isLoading: false,
        };
      },
      async mounted() {
        await this.load();
        this.isLoading = true;
      },
      methods: 
      {
        async load() 
        {
          for (let index = 1; index <= 30; index++) {
            let municipio = new Municipio(
              '0_' + index, 
              'teste_' + index, 
              'https://picsum.photos/350/165?random')
            this.add(municipio);
          }

          this.municipiosSearch = this.municipios
        },
        update(municipios: Array<Municipio>)
        {
          this.municipiosSearch = municipios
        },
        remove(id: String)
        {
          this.municipios = this.municipios.filter(municipio => municipio.id != id)
        },
        add(municipio: Municipio)
        {
          this.municipios.push(municipio)
        }
      },
  };
</script>

<style scoped>

.ps {
  height: 100vh;
}

.item {
  margin-top: 2rem;
  display: flex;
}

.details {
  flex: 1;
  margin-left: 1rem;
}

h3 {
  font-size: 1.2rem;
  font-weight: 500;
  margin-bottom: 0.4rem;
  color: var(--color-heading);
}

@media (min-width: 1024px) {
  .item {
    margin-top: 0;
    padding: 0.4rem 0 1rem calc(var(--section-gap) / 2);
  }

  .item:before {
    content: ' ';
    border-left: 1px solid var(--color-border);
    position: absolute;
    left: 0;
    bottom: calc(50% + 25px);
    height: calc(50% - 25px);
  }

  .item:after {
    content: ' ';
    border-left: 1px solid var(--color-border);
    position: absolute;
    left: 0;
    top: calc(50% + 25px);
    height: calc(50% - 25px);
  }

  .item:first-of-type:before {
    display: none;
  }

  .item:last-of-type:after {
    display: none;
  }
}
</style>
