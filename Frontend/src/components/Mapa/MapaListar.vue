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
      />
      <MapaPesquisar />
       <!--</v-network-graph>:event-handlers="eventHandlers"-->
    </v-container>
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { useTrajetoService } from '@/stores/trajetoService'
  import type { AxiosResponse } from 'axios';
  import { Municipio } from '@/stores/municipioService'
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
          nodes: {
            // node1: { name: "Node 1" },
            // node2: { name: "Node 2" },
            // node3: { name: "Node 3" },
            // node4: { name: "Node 4" },
            // node5: { name: "Node 5" },
            // node6: { name: "Node 6" },
            // node7: { name: "Node 7" },
            // node8: { name: "Node 8" },
            // node9: { name: "Node 9" },
            // node10: { name: "Node 10" },
            // node11: { name: "Node 11" },
            // node12: { name: "Node 12" },
            // node13: { name: "Node 13" },
            // node14: { name: "Node 14" },
            // node15: { name: "Node 15" },
            // node16: { name: "Node 16" },
            // node17: { name: "Node 17" },
            // node18: { name: "Node 18" },
            // node19: { name: "Node 19" },
            // node20: { name: "Node 20" },
          } as any,
          edges: {
            // edge1: { source: "node1", target: "node2" },
            // edge2: { source: "node2", target: "node3" },
            // edge3: { source: "node3", target: "node4" },
            // edge4: { source: "node4", target: "node5" },
            // edge5: { source: "node5", target: "node6" },
            // edge6: { source: "node6", target: "node7" },
            // edge7: { source: "node7", target: "node8" },
            // edge8: { source: "node8", target: "node9" },
            // edge9: { source: "node9", target: "node10" },
            // edge10: { source: "node10", target: "node11" },
            // edge11: { source: "node11", target: "node12" },
            // edge12: { source: "node12", target: "node13" },
            // edge13: { source: "node13", target: "node14" },
            // edge14: { source: "node14", target: "node15" },
            // edge15: { source: "node15", target: "node16" },
            // edge16: { source: "node16", target: "node17" },
            // edge17: { source: "node17", target: "node18" },
            // edge18: { source: "node18", target: "node19" },
            // edge19: { source: "node19", target: "node20" }
          } as any,
          layouts: {
            nodes: {
              /* 
              node1: { x: 50, y: 0 },
              node2: { x: 0, y: 75 },
              node3: { x: 200, y: 75 },
              */
            },
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
