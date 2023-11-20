<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld    from './components/HelloWorld.vue'

import CircularOverlay     from './components/CircularOverlay.vue'
import TextOverlay from './components/TextOverlay.vue'
import AuthGuard   from './components/AuthGuard.vue'

import MunicipioListar from './components/Municipio/MunicipioListar.vue'
import MunicipioItem   from './components/Municipio/MunicipioItem.vue'
import MunicipioPesquisar from './components/Municipio/MunicipioPesquisar.vue'

import MapaListar    from './components/Mapa/MapaListar.vue'
import MapaPesquisar from './components/Mapa/MapaPesquisar.vue'

import VeiculoListar from './components/Veiculo/VeiculoListar.vue'
import VeiculoItem   from './components/Veiculo/VeiculoItem.vue'
import VeiculoPesquisar from './components/Veiculo/VeiculoPesquisar.vue'

import HeaderNav from '@/components/HeaderNav.vue'
</script>

<script lang="ts">
  export default {
    name: "App",
    components: {
      HelloWorld,
      CircularOverlay,
      TextOverlay,
      AuthGuard,

      MunicipioItem,
      MunicipioListar,
      MunicipioPesquisar,

      MapaListar,

      
      VeiculoItem,
      VeiculoListar,
      VeiculoPesquisar,
      HeaderNav
    },
    data(){
    return {
      appTitle: 'Awesome App',
      sidebar: false,
      collapsed:false,
      miniMenu: true,
      darkMode: true,
      menuItems: [
          { title: 'Municipios', path: '/Municipio_Listar', icon: 'face' },
          { title: 'Veiculos', path: '/Veiculo_Listar', icon: 'face' },
          { title: 'Mapa', path: '/Mapa_Listar', icon: 'lock_open' },
     ],
     testMenu:[
      {
        name: 'Mapa',
        icon: { text: 'map', class: 'material-icons-outlined'  },
        href: '/Mapa_Listar',
      },
      {
        name: 'Listar',
        icon: { text: 'settings', class: 'material-icons-outlined'  },
        children: [
          {
            name: 'Municipios',
            icon: { text: 'settings', class: 'material-icons-outlined' },
            href: '/Municipio_Listar',
          },
          {
            name: 'Veiculos',
            icon: { text: 'settings', class: 'material-icons-outlined' },
            href: '/Veiculo_Listar',
          },
        ]
      }
      ]
    }
  },
  methods:
  {
    update(isCollapsed:boolean){
      console.log(";)")
    }
  }
  };
</script>


<template> 
  <VueAwesomeSideBar
      v-model:miniMenu="miniMenu"
      v-model:collapsed="collapsed"
      :menu="testMenu"
      :dark="darkMode"
      vueRouterEnabel
    >

    <template v-if="miniMenu == false" #header>
      <v-row>
        <v-col cols="4" v-if="!collapsed">
            <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="100" height="100" />
        </v-col>
        <v-col cols="8">
            <HelloWorld msg="Path Finder"></HelloWorld>
        </v-col>
      </v-row>
    </template>
    <template v-else #header>
      <v-row>
        <v-col cols="4" v-if="!collapsed">
            <img alt="Vue logo" class="logo" style="opacity:0;" src="@/assets/logo.svg" width="50" height="50" />
        </v-col>
      </v-row>
    </template>

    <template #headerItem="{ header }"></template>
  </VueAwesomeSideBar>

  <v-card class="cardColor">
    <v-container>
      <v-col>
        <v-row>
            <RouterView />
        </v-row>
      </v-col>
    </v-container>
  </v-card>
</template>

<style scoped>

.cardColor
{
  background-color: var(--vt-card-main)
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
