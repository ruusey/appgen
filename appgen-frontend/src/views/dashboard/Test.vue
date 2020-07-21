<template>
    <div>
     <gmaps-map style="width: 500px; height: 300px" :options="mapOptions" />
    </div>
 
</template>
 
<script>
  import { gmapsMap } from 'x5-gmaps'
 
  export default {
    name:"Test",
    components: { gmapsMap },
    data: () => ({
      mapOptions: {
        center: { lat: -27.47, lng: 153.025 },
        zoom: 12,
      },
    }),
  }
</script> 