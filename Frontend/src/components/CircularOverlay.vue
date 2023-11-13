<template>
    <v-overlay cover
    :model-value="overlay"
    class="align-center  justify-center">
    <v-progress-circular
        color="white"
        indeterminate
        size="64"
    ></v-progress-circular>
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