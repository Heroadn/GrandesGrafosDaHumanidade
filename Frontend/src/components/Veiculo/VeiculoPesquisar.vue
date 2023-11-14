<script setup lang="ts">
</script>

<template>
    <v-container v-if="isLoading" class="pa-1">
        <v-card-title color="orange-lighten-2" variant="text" >Pesquisar</v-card-title>
        <v-form ref="form" @submit.prevent="procurar">
            <v-col>
                <v-row>
                    <v-text-field v-model="nome" placeholder="Nome do municipio" />
                </v-row>
            </v-col>
        </v-form>
    </v-container>
</template>

<script lang="ts">
  import { mapStores } from 'pinia'
  import type { PropType } from 'vue';
  import type { Veiculo } from '@/stores/veiculoService';

  export default {
      computed:
      {
      },
      props:
      {
        veiculos:{
          type: Array<Veiculo>,
          required: true
        }, 
      },
      data: () =>  
      {
        return {
          resultados: [] as Veiculo[],
          nome: '',
          isLoading: false,
          overlay: false
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
        procurar()
        {
          this.resultados = this.veiculos.filter(veiculo => veiculo.nome.match(this.nome));
          this.$emit('searchResults', this.resultados);
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
