<script setup lang="ts">

</script>

<template>
  <!--color="orange-lighten-2" variant="text"-->
  <v-container v-if="isLoading" class="pa-1"  style="min-height: 100vh;">
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
        </v-form>
    </v-container>
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
          source: '',
          destination: '',
          shortest_distance: '',
          shortest_path: [] as any,
          isLoading: false
        };
      },
      async mounted() {
        this.source = "cidade_a"
        this.destination = "cidade_f"
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
            let response = await this.trajetoServiceStore.shortestPath(this.source, this.destination);
            this.request(response, 
            successRes => 
              { 
                this.shortest_distance = response.data.shortest_distance;
                this.shortest_path = response.data.shortest_path;
                this.$emit('searchResults', new Trajeto(this.shortest_distance, this.shortest_path));
              }, 
            failedRes => {})
              
      
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
