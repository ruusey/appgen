<template>
    <div>
        <v-snackbar v-for="(item,index) in notificationItems"
                    :value="true"
                    :timeout="item.timeout"
                    :color="item.color">
            {{ item.text }}
            <v-btn color="secondary" text @click="removeItem(item.id)">
                Close
            </v-btn>
        </v-snackbar>
    </div>
</template>

<script>
    import { mapState } from 'vuex';
    export default {
        name: "NotificationQueue",
        computed: {
            ...mapState(['notificationItems'])
        },
        methods: {
            removeItem(id){
                this.$store.dispatch('REMOVE_NOTIFICATION', id);
            }
        }
    }
</script>

<style scoped lang="scss">
</style>
