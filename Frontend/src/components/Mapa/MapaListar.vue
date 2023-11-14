<script setup lang="ts">
import MapaPesquisar from './MapaPesquisar.vue';
</script>

<template>
  <v-card 
      class="cardColor mx-auto" dark>
    <v-container v-if="isLoaded" class="pa-1" style="max-height: 100vh;">
      <v-network-graph
        class="graph"
        :nodes="nodes"
        :edges="edges"
        :layouts="layouts"
        :configs="configs"
      />
      <MapaPesquisar />
       <!--</v-network-graph>:event-handlers="eventHandlers"-->
    </v-container>
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { reactive, ref } from "vue"
  import * as vNG from "v-network-graph"

  import { useTrajetoService } from '@/stores/trajetoService'
  import type { AxiosResponse } from 'axios';
  import { Municipio } from '@/stores/municipioService'

  import { ForceLayout } from "v-network-graph/lib/force-layout"
  import type { ForceNodeDatum, ForceEdgeDatum } from "v-network-graph/lib/force-layout"
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
          nodes: {} as any,
          edges: {} as any,
          layouts: {
            nodes: {
            },
          },
          configs :{
            view: {
              scalingObjects: true,
              minZoomLevel: 1,
              maxZoomLevel: 1,
              layoutHandler: new ForceLayout({
              positionFixedByDrag: false,
              positionFixedByClickWithAltKey: true,
              createSimulation: (d3, nodes, edges) => {

                  // * The following are the default parameters for the simulation.
                  const forceLink = d3.forceLink<ForceNodeDatum, ForceEdgeDatum>(edges).id((d:any) => d.id)
                  return d3
                    .forceSimulation(nodes)
                    .force("edge", forceLink.distance(100))
                    .force("charge", d3.forceManyBody())
                    .force("collide", d3.forceCollide(50).strength(0.2))
                    .force("center", d3.forceCenter().strength(0.05))
                    .alphaMin(0.001)
              }
              })}
          },
          isLoaded: false
        };
      },
      async mounted() {
        await this.load();
        this.isLoaded = true;
      },
      methods: 
      {
        async load() 
        {
          let response = await this.trajetoServiceStore.allNodes(0);

          this.request(response, 
          successRes => 
            { 
              let nodes = successRes.data.graph
              
              for(const node in nodes)
              {
                const source: string = node;
                const destinations = nodes[node].map(
                  (element:any) => {
                    return { 
                    source: element.source,
                    target: element.destination,
                    weight: element.weight }
                  })
                this.add({name: source}, destinations)
              }
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
        add(
          node: {name: string}, 
          edges: [{source: '', target: '', weight: ''}])
        {
          this.nodes[node.name] = node;
          
          edges.forEach(edge => {
            this.edges[edge.source] = edge;
          });
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
