<script setup lang="ts">
import MapaPesquisar from './MapaPesquisar.vue';
import MapaSelecionar from '@/components/Mapa/MapaSelecionar.vue';
</script>

<template>
  <v-row>
    <v-col>
      <div style="float: right; position: absolute;z-index: 1;">
        <v-app id="app-container">
          <v-row>
            <v-col>
              <v-row>
                <v-btn
                block
                color="black"
                type="submit"
                @click="openMapMenu">
                Procurar
                </v-btn>
              </v-row>
              <v-row>
                <v-btn
                block
                color="black"
                type="submit"
                @click="resetar">
                Resetar
                </v-btn>
              </v-row>
            </v-col>
          </v-row>
          <v-row>
          </v-row>
          <v-row>
          </v-row>
          <v-row>
          </v-row>
          <v-row>
            <v-btn
            icon=" mdi-information-box-outline"
            @click="openInfoMenu"/>
          </v-row>
        </v-app>
      </div>     

      <!--
          <v-menu>
              <template v-slot:activator="{ props }">
                <v-btn
                  color="black"
                  v-bind="props">
                  Pesquisar
                </v-btn>
              </template>
              <v-list>
                <v-list-item style="max-height: 20vh;">
                  <MapaPesquisar @searchResults="update"/>
                </v-list-item>
              </v-list>
            </v-menu>
      -->
      
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

  <v-card>
    <v-overlay cover
    :model-value="overlayInfoMenu"
    update="false"
    class="align-center  justify-center">
      <v-card class="cardColor mx-auto pa-6" dark>
        Clique em Procurar, para encontrar o caminho de menor custo
        <br>
        Clique no primeiro nodo para selecionar a orim,
        os nodos subsequentes clicados sao destino,
        ao final ele dara o caminho de menor custo
        <br>        
        Clique em resetar para resetar os caminhos e o
        primeiro nodo clicado
      </v-card>
    </v-overlay>
  </v-card>

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
  import { add } from 'v-network-graph/lib/modules/vector2d';
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

  //TODO: mostrar selecionado tanto para overlay
  //quando para nodos clicados
  
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
              minZoomLevel: 0.60,
              maxZoomLevel: 0.60,
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
                  color: (edge: any) => "blue",
                  dasharray: (edge: any) =>  (edge.dashed ? "4" : "0")
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
          overlayMapMenu: false,
          overlayInfoMenu: false,
          isMenu: false,
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
