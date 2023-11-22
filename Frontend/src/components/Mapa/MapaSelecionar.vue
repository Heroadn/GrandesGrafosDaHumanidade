<script setup lang="ts">
</script>
  
<template>
</template>

<script lang="ts">
  import type { AxiosResponse } from 'axios';
  import { mapStores } from 'pinia'
  import { useTrajetoService } from '@/stores/trajetoService'
  import { Trajeto } from '@/stores/trajetoService'

  export default {
      computed:
      {
        ...mapStores(useTrajetoService)
      },
      props:
      {
        source:  {
          type: String,
          default: ''
        },
        target:  {
          type: String,
          default: ''
        },
      },
      watch: { 
        source: function(newVal, oldVal) { // watch it
            this.procurar();
        },
        target: function(newVal, oldVal) { // watch it
            this.procurar();
        }
      },
      data: () =>  
      {
        return {
          shortest_distance: '',
          shortest_path: [] as any,
          isLoading: false
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
        async request(
          response: AxiosResponse<any, any>, 
          success : (response: AxiosResponse<any, any>) => void,
          failed  : (response: AxiosResponse<any, any>) => void)
        {
          let status = (await response.status);
          if(status == 200)
            success(response);
          else
            failed(response);
        },
        async procurar()
        {
          const source = this.source;
          const target = this.target;

          //if the user didnt selected anything
          if(source.length > 0 && target.length > 0)
          {
            this.request(
                await this.trajetoServiceStore.shortestPath(source, target), 
            successRes => 
                { 
                this.shortest_distance = successRes.data.shortest_distance;
                this.shortest_path     = successRes.data.shortest_path;
                this.$emit('searchResults', new Trajeto(this.shortest_distance, this.shortest_path));
                }, 
            failedRes => {
                return;
            })
          }
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

.graph {
  width: 95vh;
  height: 90vh;
  border: 0px solid #000;
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
