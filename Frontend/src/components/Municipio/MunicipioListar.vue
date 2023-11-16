<script setup lang="ts">
import MunicipioItem from '@/components/Municipio/MunicipioItem.vue' 
import MunicipioPesquisar from './MunicipioPesquisar.vue';
</script>

<template>
  
  <v-card>
    <v-container class="pa-1" style="max-height: 100vh; min-width: 10vh; max-width: 60vh;">
      <MunicipioPesquisar @searchResults="update" :municipios="municipios"/>
      
      <perfect-scrollbar>
        <v-item-group multiple>
          <v-row v-if="isLoaded">
            <v-col v-for="municipio, i in municipiosSearch"
              :key="i"
              cols="12"
              md="4">
              <MunicipioItem 
                :municipio="municipio" @onClick="onClick"/>
            </v-col>
          </v-row>
        </v-item-group>
      </perfect-scrollbar>
    </v-container>
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { useMunicipioService } from '@/stores/municipioService'
  import { Municipio } from '@/stores/municipioService'
  import type { AxiosResponse } from 'axios';
 

  export default {
      computed:
      {
        ...mapStores(useMunicipioService)
      },
      data: () =>  
      {
        return {
          municipios: [] as Municipio[],
          municipiosSearch: [] as Array<Municipio>,
          isLoaded: false,
          overlay: true
        };
      },
      async mounted() {
        await this.load();
      },
      methods: 
      {
        async load() 
        {
          let response = await this.municipioServiceStore.all(0);

          this.request(response, 
          successRes => 
            { 
              let municipios = successRes.data.nodes

              for(const nome in municipios)
                this.add(new Municipio(municipios[nome], "", "", "", ""))

              this.municipiosSearch = this.municipios
              this.isLoaded = true; 
            }, 
          failedRes => {})
        },
        async request(
          response: AxiosResponse<any, any>, 
          success : (response: AxiosResponse<any, any>) => void,
          failed  : (response: AxiosResponse<any, any>) => void)
        {
          let status = (await response.status);
          if(status == 200)
            success(response);
          else
            failed(response);
        },
        update(municipios: Array<Municipio>)
        {
          this.municipiosSearch = municipios
        },
        remove(nome: String)
        {
          this.municipios = this.municipios.filter(municipio => municipio.nome != nome)
        },
        add(municipio: Municipio)
        {
          this.municipios.push(municipio)
        },
        onClick(nome:string)
        {
          this.$emit('onClick', nome);
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
