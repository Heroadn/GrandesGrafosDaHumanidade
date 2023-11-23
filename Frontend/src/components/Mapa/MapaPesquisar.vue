<script setup lang="ts">
import MunicipioListar from '@/components/Municipio/MunicipioListar.vue';
import MapaSelecionar from '@/components/Mapa/MapaSelecionar.vue';
</script>
  
<template>
  <!--:prepend-icon="buttons['sourceBtn'].currentIcon"-->

  <v-menu
    v-model="menuOrigem"
    :close-on-content-click="false"
    location="bottom"
  >
    <template v-slot:activator="{ props }">
      <v-card
        color="black"
        variant="outlined" 
        type="submit"
        width="30vh"
        v-bind="props"
        @click="selectedBtn = 'sourceBtn';">
        <v-container>
          <v-row>
            <h4 style="color: black;">Origem</h4>
          </v-row>
          <v-row>
            <h5 style="margin-left:2vh;">{{ buttons['sourceBtn'].value }}</h5>
          </v-row>
        </v-container>
      </v-card>
    </template>

    <v-card style="overflow: hidden;">
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-card-text>
          <MunicipioListar @onClick="onClick"/>
        </v-card-text>
      </v-card-actions>
    </v-card>
  </v-menu>

  <v-menu
    v-model="menuDestino"
    :close-on-content-click="false"
    location="bottom"
  >
    <template v-slot:activator="{ props }">
      <v-card
        color="black"
        variant="outlined" 
        type="submit"
        width="30vh"
        v-bind="props"
        @click="selectedBtn = 'targetBtn';">
        <v-container>
          <v-row>
            <h4 style="color: black;">Destino</h4>
          </v-row>
          <v-row>
            <h5 style="margin-left:2vh;">{{ buttons['targetBtn'].value }}</h5>
          </v-row>
        </v-container>
      </v-card>
    </template>

    <v-card style="overflow: hidden;">
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-card-text>
          <MunicipioListar @onClick="onClick"/>
        </v-card-text>
      </v-card-actions>
    </v-card>
  </v-menu>

  <MapaSelecionar 
    :source="buttons['sourceBtn'].value"
    :target="buttons['targetBtn'].value"
    @searchResults="update"/>
</template>

<script lang="ts">
  import type { AxiosResponse } from 'axios';
  import { mapStores } from 'pinia'
  import { useTrajetoService } from '@/stores/trajetoService'
  import type { Trajeto } from '@/stores/trajetoService'

  class ActionButton
  {
    value =  '';
    alternativeIcon = ''; 
    currentIcon =  ''; 
    isOn =  false;

    constructor(
      altIcon: string,
      currentIcon: string,
      value: string = '')
    {
      this.value = value;
      this.alternativeIcon = altIcon;
      this.currentIcon = currentIcon;
    }

    onAction(data: string){ 
      this.value = data
      this.isOn = true
      this.swapIcon()
    }

    swapIcon()
    {
      let tmp = this.currentIcon
      this.currentIcon = this.alternativeIcon
      this.alternativeIcon = tmp
    }
  }

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
          menuOrigem: false,
          menuDestino: false,
          buttons: {
            'sourceBtn': new ActionButton(
              'mdi-check',
              'mdi-alert-circle',
              'Selecione uma cidade'),
            'targetBtn': new ActionButton(
              'mdi-check',
              'mdi-alert-circle',
              'Selecione uma cidade')
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
        onClick(nome:string)
        {
          this.overlay = false
          this.menuOrigem = false
          this.menuDestino = false
          this.buttons[this.selectedBtn].onAction(nome);
        },
        update(trajeto: Trajeto)
        {
          this.$emit('searchResults', trajeto);
        },
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

h5
{
  margin-left:2vh;
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

@media screen and (min-width: 1920px) {
  h4 {
    margin-bottom: 0.4rem;
    color: black;
  }

  h5 {
    margin-bottom: 0.4rem;
    color: black;
  }
}
</style>
