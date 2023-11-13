import { createRouter, createWebHistory } from 'vue-router'
import HomeView  from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'

import MunicipioListarView from '../views/Municipio/MunicipioListarView.vue'
import MapaListarView from '../views/Mapa/MapaListarView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: AboutView
    },
    {
      path: '/municipio_listar',
      name: 'municipio_listar',
      component: () => MunicipioListarView
    },
    {
      path: '/mapa_listar',
      name: 'mapa_listar',
      component: () => MapaListarView
    }
  ]
})

export default router