<script setup lang="ts">
import MunicipioListar from '@/components/Municipio/MunicipioListar.vue';
</script>
  
<template>
  <!--color="orange-lighten-2" variant="text"-->
  <v-container v-if="isLoading" class="pa-1"  style="min-height: 100vh;">
    <!--
        <v-form ref="form" @submit.prevent="procurar">
            <v-col>
                <v-row>
                  <v-text-field v-model="source" placeholder="Source" />
                  <v-text-field v-model="destination" placeholder="Destine" />
                  <v-card-actions>
                    <v-btn
                    color="orange-lighten-2"
                    variant="outlined" 
                    type="submit">
                      Pesquisar
                    </v-btn>
                    <v-spacer></v-spacer>
                  </v-card-actions>
                </v-row>
            </v-col>
        </v-form>-->

        <v-row no-gutters>
          <v-col>
            <v-btn
              :prepend-icon="buttons['sourceBtn'].currentIcon"
              color="purple-lighten-2"
              variant="outlined" 
              type="submit"
              @click="selectedBtn = 'sourceBtn'; overlay = true ">
              Origem
            </v-btn>
          </v-col>
          <v-col>
            <v-btn
              :prepend-icon="buttons['targetBtn'].currentIcon"
              color="purple-lighten-2"
              variant="outlined" 
              type="submit"
              @click="selectedBtn = 'targetBtn'; overlay = true">
              Destino
            </v-btn>
          </v-col>
          <v-col>
            <v-btn
              color="purple-lighten-2"
              variant="outlined" 
              type="submit"
              @click="procurar">
              Pesquisar
            </v-btn>
          </v-col>
        </v-row>  
    </v-container>

    
  <v-overlay cover
    :model-value="overlay"
    update="false"
    class="align-center  justify-center">
      <v-card 
        class="cardColor mx-auto pa-6" dark>
        <MunicipioListar @onClick="onClick"/>
        <slot></slot>
      </v-card>
    </v-overlay>
</template>

<script lang="ts">
  import type { AxiosResponse } from 'axios';
  import { mapStores } from 'pinia'
  import { useTrajetoService } from '@/stores/trajetoService'
  import { Trajeto } from '@/stores/trajetoService'
  /*
  import { EventHandlers } from "v-network-graph"
  const eventHandlers: EventHandlers = {
    "node:click": ({ node, event }) => {
      if (event.ctrlKey) {
        // ...
      }
    }
  }*/

  export default {
      computed:
      {
        ...mapStores(useTrajetoService)
      },
      data: () =>  
      {
        return {
          shortest_distance: '',
          shortest_path: [] as any,
          isLoading: false,
          overlay: false,
          buttons: {
            'sourceBtn': {
              value: '',
              altIcon: 'mdi-check', 
              currentIcon: 'mdi-alert-circle',
              isOn: false,
              onAction(data: string){ 
                this.value = data
                this.isOn = true
                this.swapIcon()
              },
              swapIcon()
              {
                let tmp = this.currentIcon
                this.currentIcon = this.altIcon
                this.altIcon = tmp
              }
            },
            'targetBtn': {
              value: '',
              altIcon: 'mdi-check', 
              currentIcon: 'mdi-alert-circle', 
              isOn: false,
              onAction(data: string){ 
                this.value = data
                this.isOn = true
                this.swapIcon()
              },
              swapIcon()
              {
                let tmp = this.currentIcon
                this.currentIcon = this.altIcon
                this.altIcon = tmp
              }
            }
          } as any,
          selectedBtn: '' as string
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
        async procurar()
        {
          const sourceBtn = this.buttons['sourceBtn'];
          const targetBtn = this.buttons['targetBtn'];

          const source = sourceBtn.value;
          const target = targetBtn.value;

          //if the user didnt selected anything
          if(sourceBtn.value == '' && targetBtn.value == '')
            return;

          let response = await this.trajetoServiceStore.shortestPath(source, target);
          this.request(response, 
          successRes => 
            { 
              this.shortest_distance = response.data.shortest_distance;
              this.shortest_path = response.data.shortest_path;
              this.$emit('searchResults', new Trajeto(this.shortest_distance, this.shortest_path));
            }, 
          failedRes => {})
        },
        onClick(nome:string)
        {
          console.log(nome)
          this.overlay = false
          this.buttons[this.selectedBtn].onAction(nome);
        }
      },
  };
</script>

<style scoped>
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

.graph {
  width: 95vh;
  height: 90vh;
  border: 0px solid #000;
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
