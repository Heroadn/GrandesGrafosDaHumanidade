<template>
  <v-card
    class="mx-auto"
    width ="100%"
    height ="20vh"
    rounded="0">
    <v-img
      height="100%"
      cover 
      :src="municipio.image">
      <v-card height="10%" class="mx-auto pa-6" rounded="0">
        <v-row>
          <v-label>{{ municipio.nome }}</v-label> 
        </v-row>
        <v-spacer></v-spacer>
      </v-card>
    </v-img>
  </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { useMunicipio, Municipio } from '@/stores/municipioService'
  
  export default {
      computed:
      {
        ...mapStores(useMunicipio)
      },
      props:
      {
        municipio:  {
          type: Municipio,
          required: true
        },
      },
      data: () =>  
      {
        return {
          //image : new String("https://picsum.photos/350/165?random") ,
        };
      },
      mounted() {
      },
      methods: 
      {
        limitText(value:String | string)
        {
          let val: String = value.slice(0, 255);
          return new String(val + "...");
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
