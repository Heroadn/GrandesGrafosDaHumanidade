<script setup lang="ts">
import MapaPesquisar from './MapaPesquisar.vue';
</script>

<template>
  <v-row>
    <v-col>
      <v-btn
        color="green-lighten-2"
        type="submit"
        @click="openMapMenu">
        Pesquisar
      </v-btn>
                    
      <v-network-graph
          class="graph"
          :nodes="nodes"
          :edges="edges"
          :layouts="layouts"
          :paths="paths"
          :configs="configs"
          :layers="layers"
        >
          <template #edge-label="{ edge, ...slotProps }">
            <v-edge-label :text="edge.label" align="center" vertical-align="above" v-bind="slotProps" /> 
          </template>

          <template #badge="{ scale }" v-if="false">
          <!--
            If the `view.scalingObjects` config is `false`(default),
            scaling does not change the display size of the nodes/edges.
            The `scale` is passed as a scaling factor to implement
            this behavior. -->
            <circle
              v-for="(pos, node) in layouts.nodes"
              :key="node"
              :cx="10"
              :cy="10"
              :r="4 * scale"
              :fill="nodes[node].active ? '#00cc00' : '#ffffff'"
              style="pointer-events: none"
            />
          </template>

          <!-- Additional layer -->
          <template #menuMap>
            <v-card color="black">
              <v-row>
                <v-btn
                  color="green-lighten-2"
                  type="submit"
                  @click="openMapMenu">
                  Pesquisar
                </v-btn>
              </v-row>
            </v-card>
          </template>
            
      </v-network-graph>
    </v-col>
  </v-row>

  <v-card>
    <v-overlay cover
    :model-value="overlayMapMenu"
    update="false"
    class="align-center  justify-center">
      <v-card class="cardColor mx-auto pa-6" dark>
        <MapaPesquisar @searchResults="update"/>
      </v-card>
    </v-overlay>
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import type * as vNG from "v-network-graph"

  import { Trajeto, useTrajetoService } from '@/stores/trajetoService'
  import type { AxiosResponse } from 'axios';

  import { ForceLayout } from "v-network-graph/lib/force-layout"
  import type { ForceNodeDatum, ForceEdgeDatum } from "v-network-graph/lib/force-layout"

  interface Node extends vNG.Node {
    size: number
    color: string
    label?: boolean
  }

  interface Edge extends vNG.Edge {
    width: number
    color: string
    dashed?: boolean
  }

  export default {
      computed:
      {
        ...mapStores(useTrajetoService)
      },
      data: () =>  
      {
        return {
          nodes: {} as Record<string, Node>,
          edges: {} as Record<string, Edge>,
          paths: {} as any,
          layers:{
            // {layername}: {position}
            menuMap: "root",
            badge: "nodes"
          },
          layouts: {
            nodes: {
            },
          },
          destinations: [] as any,
          configs :{
            view: {
              scalingObjects: true,
              minZoomLevel: 1.05,
              maxZoomLevel: 1.05,
              layoutHandler: new ForceLayout({
              positionFixedByDrag: false,
              positionFixedByClickWithAltKey: true,
              createSimulation: (d3, nodes, edges) => {

                  // * The following are the default parameters for the simulation.
                  const forceLink = d3.forceLink<ForceNodeDatum, ForceEdgeDatum>(edges).id((d:any) => d.id)
                  return d3
                    .forceSimulation(nodes)
                    .force("edge", forceLink.distance(50).strength(0.01))
                    .force("charge", d3.forceManyBody())
                    .force("collide", d3.forceCollide(80).strength(0.1))
                    .force("center", d3.forceCenter().strength(0.01))
                    .alphaMin(0.001)
              }
              })},
              node: {
                normal: {
                  type: "circle",
                  color: (node: any) => node.color,
                },
                hover: {
                  radius: (node: any) => node.size + 2,
                  color: (node: any) => node.color,
                },
                selectable: true,
                label: {
                  visible: true,
                },
                focusring: {
                  color: "darkgray",
                },
              },
              edge: {
                normal: {
                  color: (edge: any) => edge.color,
                  dasharray: (edge: any) => (edge.dashed ? "8" : "0")
                },
                summarize: true,
                summarized: {
                  label: {
                    fontSize: 10,
                    color: "#4466cc",
                  },
                  shape: {
                    type: "rect",
                    radius: 6, // for type is "circle"
                    width: 12,
                    height: 12,
                    borderRadius: 3,
                    color: "#ffffff",
                    strokeWidth: 1,
                    strokeColor: "#4466cc",
                    strokeDasharray: "0",
                  },
                  stroke: {
                    width: 5,
                    color: "#4466cc",
                    dasharray: "0",
                    linecap: "butt",
                    animate: false,
                    animationSpeed: 50,
                  },
                },
                marker: {
                  source: { type: "arrow" },
                },
                margin: 4,
                gap: 10,
                keepOrder: "clock",
                
              },
              path: {
              visible: true,
              normal: {
                width: 10,
                color: (p: any) => "red",
              },
            },
          },
          isLoaded: false,
          overlayMapMenu: false
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
          this.loadNodes();
          this.isLoaded = true; 
        },
        async loadNodes()
        {
          let response = await this.trajetoServiceStore.allNodes(0);

          this.request(response, 
          successRes => 
            { 
              let nodes = successRes.data.graph

              for(const node in nodes)
              {
                //mapeando edges para formato da lib
                const source: string = node;
                const edges = nodes[node].map(
                  (element:any) => {
                    return { 
                    source: element.source,
                    target: element.destination,
                    label: element.weight}
                  })
                
                //usado para procurar caminho de nodos  
                this.destinations[node] = nodes[node].map(
                    (element:any) => this._createNodeName(element.source, element.destination));

                //adiciona nodo e edges
                this.add(
                  {name: source, color: "red", size: 10}, 
                  edges)
              }
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
        update(trajeto: Trajeto)
        {
          this.trace(trajeto)
        },
        trace(trajeto: Trajeto)
        {
          /*
          //path1: { edges: ["edge1", "edge3", "edge5", "edge7"] },*/
          this.paths = {}

          let path = trajeto.shortestPath[Symbol.iterator]();
          let source = path.next().value
          let target = path.next().value
          let result = [] as any

          let id = source + '_'+ target
          while(this.edges[id] != undefined)
          {
            result.push(id)
            source = target
            target = path.next().value
            id = source + '_'+ target
          }
          
          this.paths[trajeto.shortestDistance] = {edges: result}
        },
        add(
          node: {name: string, color: string, size: number}, 
          edges: [{source: '', target: '', weight: '', width: number, color: string, dashed: boolean}])
        {
          this.nodes[node.name] = node;
          
          edges.forEach(edge => {
            edge.color = "black";
            edge.dashed = true;
            this.edges[this._createNodeName(edge.source, edge.target)] = edge;
          });
        },
        openMapMenu()
        {
          this.overlayMapMenu = true;
        },
        _createNodeName(source:string, destination: string)
        {
          return source + '_' + destination
        }
      },
  };
</script>

<style scoped>

#container {
  position: relative;
}
#container canvas, #overlay {
  position: absolute;
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
