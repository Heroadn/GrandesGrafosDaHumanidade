<template>
    <v-overlay cover
    :model-value="overlay"
    update="false"
    class="align-center  justify-center">
      <v-card 
        class="cardColor mx-auto pa-6" dark>
        <v-card-title color="orange-lighten-2" variant="text" >{{text}}</v-card-title>
        <slot></slot>
      </v-card>
    </v-overlay>
</template>


<script lang="ts">

  export default {
      computed:
      {

      },
      props:
      {
        overlay: Boolean,
        text: String
      },
      data: () =>  
      {
      },
      mounted() {
        
      },
      methods: 
      {
      },
  };
</script>