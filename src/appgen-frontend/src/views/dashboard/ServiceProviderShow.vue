<template>
    <div>
        <h2 class="pl-5 pa-2">{{ sp.userName }}</h2>
        <v-skeleton-loader v-if="loading" class="pa-2" type="heading" max-width="500"></v-skeleton-loader>
        <v-col :cols="8">
                    <v-data-table :headers="headers"
                                  :items="geoloc"
                                  :loading="loading"
                                  :search="search" class="elevation-1">
                        <template v-slot:top>
                            <v-toolbar flat color="white">
                                <v-toolbar-title>Service Provider</v-toolbar-title>
                                <v-divider class="mx-4" inset vertical></v-divider>
                                <v-text-field
                                        v-model="search"
                                        label="Search"
                                        single-line
                                        hide-details
                                ></v-text-field>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                        </template>
                        <template v-slot:no-data>
                            <div>Select SP</div>
                        </template>
                    </v-data-table>
                    
                </v-col>
               
    </div>

</template>

<script>
    import ServiceProviderService from '../../services/serviceProvider.js';
    export default {
        name: "ServiceProviderShow",
        data: () => {
            return {
                sp: {},
                loading: false,
                loadingSites: true,
                loadingServiceHistory: false,
                search: '',
                headers: [
                    {
                        text: 'Lat',
                        value: 'lat',
                        sortable: true,
                    },
                    {
                        text: 'Long',
                        value: 'lng',
                        sortable: true,
                    },
                    {
                        text: 'Date Time',
                        value: 'dateTime',
                        sortable: true,
                    },
                    {
                        text: 'Completed',
                        value: 'complete',
                        sortable: false,
                    }
                ],
                geoloc: []
               
            }
        },
        methods: {
            loadServiceProvider(){
                this.loading = true;
                ServiceProviderService.get(this.$route.params.id).then(res => {
                    console.log(res);
                    this.sp = res;
                    this.geoloc.push(res.loc);
                    
                }).catch(err => {
                    console.log(err)
                }).finally(() => this.loading = false);
            }
        },
        mounted(){
            this.loadServiceProvider();
            
        },
        
    }
</script>

<style scoped>

</style>
