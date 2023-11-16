<script setup lang="ts">
import VeiculoItem from '@/components/Veiculo/VeiculoItem.vue' 
import VeiculoPesquisar from './VeiculoPesquisar.vue';
</script>

<template>
  <v-card>
    <v-container class="pa-1" style="max-height: 100vh; min-width: 10vh; max-width: 60vh;">
      <VeiculoPesquisar @searchResults="update" :veiculos="veiculos"/>
      
      <perfect-scrollbar>
        <v-item-group multiple>
          <v-row v-if="isLoaded">
            <v-col v-for="veiculo, i in veiculosSearch"
              :key="i"
              cols="12"
              md="4">
              <VeiculoItem 
                :veiculo="veiculo" @onClick="onClick"/>
            </v-col>
          </v-row>
        </v-item-group>
      </perfect-scrollbar>
    </v-container>

    
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
      data: () =>  
      {
        return {
          veiculos: [] as Veiculo[],
          veiculosSearch: [] as Array<Veiculo>,
          isLoaded: false,
        };
      },
      async mounted() {
        this.veiculos.pop()
        await this.load();
      },
      methods: 
      {
        async load() 
        {
          let response = await this.veiculoServiceStore.all(0);
          let status = (await response.status);
          
          if(status == 200)
          {
            let veiculos = (await (await response).data.vehicles)

            this.veiculos = veiculos.map((element:any) => 
              new Veiculo(
                element.name, 
                element.fuel_type, 
                element.kilometersPerLiter, 
                element.speedInKmH, ""));

            this.isLoaded = true;
          }

          this.veiculosSearch = this.veiculos
        },
        update(veiculos: Array<Veiculo>)
        {
          this.veiculosSearch = veiculos
        },
        remove(nome: String)
        {
          this.veiculos = this.veiculos.filter(veiculo => veiculo.nome != nome)
        },
        add(veiculo: Veiculo)
        {
          this.veiculos.push(veiculo)
        },
        onClick(veiculo: Veiculo)
        {
          this.$emit('onClick', veiculo);
          console.log(veiculo)
        }
      },
  };
</script>

<style scoped>

.ps {
  height: 100vh;
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
