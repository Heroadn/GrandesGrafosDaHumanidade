import { createApp } from 'vue'
import { createPinia } from 'pinia'


import App from './App.vue'
import router from './router'
import './assets/main.css'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// Naive UI
import naive from 'naive-ui'

//Scrollbar
import PerfectScrollbar from 'vue3-perfect-scrollbar'
import 'vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css'

//Network Graphs
import VNetworkGraph from "v-network-graph"
import "v-network-graph/lib/style.css"


const vuetify = createVuetify({
    components,
    directives,
    theme: {
      defaultTheme: 'light'
    }
  })
  

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(vuetify)
app.use(PerfectScrollbar)
app.use(VNetworkGraph)
app.use(naive)
app.mount('#app')
