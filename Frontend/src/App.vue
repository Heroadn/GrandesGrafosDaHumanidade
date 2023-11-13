<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld    from './components/HelloWorld.vue'

import CircularOverlay     from './components/CircularOverlay.vue'
import TextOverlay from './components/TextOverlay.vue'
import AuthGuard   from './components/AuthGuard.vue'

import MunicipioListar from './components/Municipio/MunicipioListar.vue'
import MunicipioItem   from './components/Municipio/MunicipioItem.vue'
import MunicipioPesquisar from './components/Municipio/MunicipioPesquisar.vue'

import MapaListar from './components/Mapa/MapaListar.vue'

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

      MapaListar
    },
    data(){
    return {
      appTitle: 'Awesome App',
      sidebar: false,
      menuItems: [
          { title: 'Municipios', path: '/Municipios_Listar', icon: 'face' },
          { title: 'Mapa', path: '/Mapa_Listar', icon: 'lock_open' }
     ]
    }
  },
  };
</script>


<template> 
    <header>
      <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

        <div class="wrapper">
          <HelloWorld msg="Path Finder" />
          <!--
          <nav v-for="item, i in menuItems">
            <RouterLink :to="item.path">{{ item.title }}</RouterLink>
          </nav> -->

          <nav>
            <RouterLink to="/Municipio_Listar">Municipios</RouterLink>
            <RouterLink to="/Mapa_Listar">Mapa</RouterLink>
          </nav>
        </div>
    </header>
    
    <RouterView />

</template>

<style scoped>
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
