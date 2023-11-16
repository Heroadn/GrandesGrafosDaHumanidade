<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld    from '@/components/HelloWorld.vue'
</script>

<template> 
    <header>
        <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="100" height="100" />

        <div class="wrapper">
            <HelloWorld msg="Path Finder">
              <nav>
                <RouterLink v-for="item, i in menuItems" :to="item.path">{{ item.title }}</RouterLink>
              </nav> 
            </HelloWorld>
        </div>
    </header>
</template>

<script lang="ts">
  export default {
    name: "App",
    props:
    {
        appTitle: {},
        menuItems: [ ] as any[] 
    },
    data(){
    return {
      sidebar: false,
    }
  },
  };
</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 50vh;
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
  color: var(--color-a-active);
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
