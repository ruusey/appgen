<template>
  <!-- App.vue -->

  <v-app>
    <router-view></router-view>
    <notifications></notifications>
  </v-app>
</template>


<script>
  import Notifications from './components/NotificationQueue.vue';
  export default {
      data: () => {
        return {

        }
      },
      components: {
        'notifications': Notifications,
      }
  }
</script>

<style lang="scss">

</style>
