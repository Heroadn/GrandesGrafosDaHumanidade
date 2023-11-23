<script setup lang="ts">
import MapaPesquisar from './MapaPesquisar.vue';
import MapaSelecionar from '@/components/Mapa/MapaSelecionar.vue';

import VeiculoListar from '@/components/Veiculo/VeiculoListar.vue';
</script>

<!-- TODO: MOSTRAR DISTANCIA TOTAL PERCORRIDA -->
<!-- TODO: EFEITO DE OPACIDADE COM CANVAS .v-ng-canvas.show  -->
<template>
  <v-app id="app-container" style="position: absolute;z-index: 2;padding: 12px 12px 12px 12px;">
    <v-row>
      <v-col>
        <v-row>
          <v-btn
          size="10vh"
          class="rounded-circle"
          @click="resetar">
            <v-icon size="4.2vh">
              mdi-redo
            </v-icon>
          </v-btn>
        </v-row>
        <v-row>
          <v-menu
            v-model="menuMapa"
            :close-on-content-click="false"
            location="end"
          >
            <template v-slot:activator="{ props }">
              <v-btn
              color="black"
              size="10vh"
              class="rounded-circle"
              v-bind="props">
                <v-icon size="4.2vh">
                  mdi-map
                </v-icon>
              </v-btn>
            </template>


            <v-card height="auto" style="overflow: hidden;">
              <v-spacer></v-spacer>
                <v-card-text>
                    <MapaPesquisar @searchResults="update"/>
                    <VeiculoListar />
                    <v-slider label="Combustivel"></v-slider>
                    <v-slider label="Motoristas "></v-slider>
                    <v-slider label="Alimentação"></v-slider>
                </v-card-text>

              <v-card-actions>
 
              </v-card-actions>
            </v-card>

          </v-menu>
        </v-row>
        <v-row>
          <v-menu
            v-if="false"
            v-model="menuInfo"
            :close-on-content-click="false"
            location="end"
          >
            <template v-slot:activator="{ props }">
              <v-btn
              color="white"
              size="10vh"
              class="rounded-circle"
              v-bind="props">
                <v-icon size="4.2vh">
                  mdi-information-box-outline
                </v-icon>
              </v-btn>
            </template>

            <v-card height="25vh" style="overflow: hidden;">
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-card-text>
                  
                  Clique em 
                  <v-btn
                  color="black"
                  icon="mdi-map"/>
                  para encontrar o caminho de menor custo
                  <br>
                  Nodos podem ser clicados,
                  O primeiro a origem os subsequentes sao destino desejado
                  <br>        
                  Clique em 
                  <v-btn
                  color="black"
                  icon="mdi-redo"/>
                  para resetar os caminhos e o
                  os nodos clicados
                </v-card-text>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-row>
        <v-row>
          <v-menu
            v-model="menuConfig"
            :close-on-content-click="false"
            location="end"
          >
            <template v-slot:activator="{ props }">
              <v-btn
              color="white"
              size="10vh"
              class="rounded-circle"
              v-bind="props">
                <v-icon size="4.2vh">
                  mdi-cog
                </v-icon>
              </v-btn>
            </template>

            <v-card height="25vh" style="overflow: hidden;">
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-card-text>
                  <v-checkbox 
                    color="black" 
                    v-model="isDistaceEnabled" 
                    label="Exibir distancias">
                  </v-checkbox>
                  <v-select
                  v-if="false"
                    :v-model="configs.edge.normal.color"
                    label="Cor das estradas"
                    :items="['Black', 'Red', 'Purple', 'Transparent']">
                  </v-select>
                  <!--edge.normal.color-->
                </v-card-text>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-row>
      </v-col>
    </v-row>
  </v-app>

  <v-network-graph
        class="graph"
        :nodes="nodes"
        :edges="edges"
        :layouts="layouts"
        :paths="paths"
        :configs="configs"
        :layers="layers"
        :event-handlers="eventHandlers"
      >
        <template #edge-label="{ edge, ...slotProps }" v-if="isDistaceEnabled">
          <v-edge-label :text="edge.label" align="center" vertical-align="above" v-bind="slotProps" /> 
        </template>
  </v-network-graph>

  <MapaSelecionar 
      :source="caminhoSelecionado.source"
      :target="caminhoSelecionado.target"
      @searchResults="update"/>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import type * as vNG from "v-network-graph"

  import { Trajeto, useTrajetoService } from '@/stores/trajetoService'
  import type { AxiosResponse } from 'axios';

  import { ForceLayout } from "v-network-graph/lib/force-layout"
  import type { ForceNodeDatum, ForceEdgeDatum } from "v-network-graph/lib/force-layout"
  import { ref, type Ref, reactive } from 'vue';

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

  class CaminhoSelecionado extends Array {
    LIMIT = 2
    source = this[0]
    target = this[1]

    constructor(...args:any) {
        super(...args);
        this[0] = ''
        this[1] = ''
    }
    unshift(value: string)
    {
      let result = super.unshift(value);

      this.source = this[0]
      this.target = this[1]
      console.log(this.source, this.target)
      if(this.length == this.LIMIT)
      {
        result = super.pop()
        super.pop()
        super.unshift(result)
      }

      return result;
    }
    push(value: string)
    {
      let result = super.unshift(value);
    
      if(this.source == undefined)
        this.source = value
      else 
        this.target = value
      return result
    }
    reset()
    {
      this.source = undefined
      this.target = undefined
    }
  }

  let caminhoSelecionado = ref(new CaminhoSelecionado()) as Ref<CaminhoSelecionado>

  let eventHandlers: vNG.EventHandlers = {
  "node:click": ({ node }) => {
      caminhoSelecionado.value.push(node);
    },
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
              layoutHandler: new ForceLayout({
              positionFixedByDrag: false,
              positionFixedByClickWithAltKey: true,
              createSimulation: (d3, nodes, edges) => {

                  // * The following are the default parameters for the simulation.
                  const forceLink = d3.forceLink<ForceNodeDatum, ForceEdgeDatum>(edges).id((d:any) => d.id)
                  return d3
                    .forceSimulation(nodes)
                    .force("edge", forceLink.distance(80).strength(0.01))
                    .force("charge", d3.forceManyBody())
                    .force("collide", d3.forceCollide(80).strength(0.1))
                    .force("center", d3.forceCenter().strength(0.01))
                    .alphaMin(0.001)
              }
              })},
              node: {
                normal: {
                  type: "circle",
                  color: (node: any) => 'black',
                },
                hover: {
                  radius: (node: any) => node.size + 2,
                  color: (node: any) => node.color,
                },
                selectable: true,
                label: {
                  visible: true,
                  direction: "center",
                  directionAutoAdjustment: true,
                  fontFamily: undefined,
                  fontSize: 18,
                  color: "#FFFFFF",
                  background: {
                    visible: true,
                    color: "#000000FF",
                    padding: {
                      vertical: 1,
                      horizontal: 4,
                    },
                    borderRadius: 2,
                  },
                },
                focusring: {
                  color: "darkgray",
                },
              },
              edge: {
                normal: {
                  color: (edge: any) => "black",
                  dasharray: (edge: any) =>  (edge.dashed ? "0" : "0")
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
                color: (p: any) => "#FFFFFFDD",
              },
            },
          },
          isLoaded: false,
          overlayMapMenu: false,
          overlayInfoMenu: false,
          isMenu: false,
          isNodesEnabled: true,
          isDistaceEnabled: true,
          menuMapa: false,
          menuInfo: false,
          menuConfig: false,
          red: 0,
          green: 0,
          blue: 0
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
                this.addNode(
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
          this.overlayMapMenu = false;
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
        addNode(
          node: {name: string, color: string, size: number}, 
          edges: [{source: '', target: '', weight: '', width: number, color: string, dashed: boolean}])
        {
          if(!this.isNodesEnabled)
            return;

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
        openInfoMenu()
        {
          this.overlayInfoMenu = true;
        },
        resetar()
        {
          this.paths = {}
          caminhoSelecionado.value.reset()
        },
        _createNodeName(source:string, destination: string)
        {
          return source + '_' + destination
        },
        "node:click"(node:any){  
          console.log("xD")
        },
        teste(node:any)
        {

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

#app-container
{
  background-color: rgba(255, 255, 255, 0.0) !important;
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
