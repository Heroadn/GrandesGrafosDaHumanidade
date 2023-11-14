<template>
      <v-card class="mx-auto pa-6" rounded="0">
        <v-row>
          <v-label>{{ veiculo.nome }}</v-label> 
        </v-row>
      </v-card>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import { useVeiculoService } from '@/stores/veiculoService'
  import { Veiculo } from '@/stores/veiculoService'
  
  export default {
      computed:
      {
        ...mapStores(useVeiculoService)
      },
      props:
      {
        veiculo:  {
          type: Veiculo,
          required: true
        },
      },
      data: () =>  
      {
        return {
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
