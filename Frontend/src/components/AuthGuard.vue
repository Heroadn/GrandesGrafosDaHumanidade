<script setup lang="ts">
import TextOverlay from '@/components/TextOverlay.vue' 
</script>

<template>
  <TextOverlay :overlay="overlay" text="Usuario não Logado " update="false" />
  <slot v-if="isLogged"></slot>
</template>


<script lang="ts">
  import { mapStores } from 'pinia'
  import { watchEffect} from "vue";
  import { useAuthService } from '@/stores/authService'
  import { useTimer } from 'vue-timer-hook';

  export default {
      computed:
      {
        ...mapStores(useAuthService)
      },
      data: () =>  
      {
        return {
          overlay: false,
          isLogged: false
        };
      },
      mounted() {
        //verificar se o token esta expirado
        this.isLogged = this.authServiceStore.isLogged; 
        this.isLogged = true;
        const router = this.$router;
        const time = new Date();
        const timer = useTimer(5);

        if(this.isLogged == false)
        {
          this.overlay = true;
          watchEffect(async () => {
            if(timer.isExpired.value) {
              router.push({ path: '/usuario_login' });
            }
          })
        }
      },
      methods: 
      {
      },
  };
</script>