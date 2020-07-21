<template>
    <div>
        <h2 class="pl-5 pa-2">{{ client.userName }}
            's Listings
        </h2>
        <v-skeleton-loader v-if="loadingLessee" class="pa-2" type="heading" max-width="500"></v-skeleton-loader>
        <v-col :cols="8">
                    <v-data-table :headers="headers"
                                  :items="jobs"
                                  :loading="loadingSites"
                                  :search="search" class="elevation-1">
                        <template v-slot:top>
                            <v-toolbar flat color="white">
                                <v-toolbar-title>Job Listings</v-toolbar-title>
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
                        <template v-slot:item.complete="{ item }">
                            <span>{{ item.complete ? 'YES' : 'NO' }}</span>
                        </template>
                        <template v-slot:no-data>
                            <div>Select Site</div>
                        </template>
                    </v-data-table>
                </v-col>
    </div>

</template>

<script>
    import ClientService from '../../services/client.js';
    export default {
        name: "ClientShow",
        data: () => {
            return {
                client: {},
                loadingLessee: false,
                loadingSites: true,
                loadingServiceHistory: false,
                search: '',
                headers: [
                    {
                        text: 'Short Description',
                        value: 'shortDescription',
                        sortable: true,
                    },
                    {
                        text: 'Long Description',
                        value: 'longDescription',
                        sortable: true,
                    },
                    {
                        text: 'Pay',
                        value: 'pay',
                        sortable: true,
                    },
                    {
                        text: 'Completed',
                        value: 'complete',
                        sortable: false,
                    }
                ],
                jobs: []
               
            }
        },
        methods: {
            loadLessee(){
                this.loadingLessee = true;
                ClientService.get(this.$route.params.id).then(res => {
                    console.log(res);
                    this.client = res;
                    this.jobs=res.jobs;
                    this.loadingSites=false;
                }).catch(err => {
                    console.log(err)
                }).finally(() => this.loadingLessee = false);
            }
        },
        mounted(){
            this.loadLessee();
            
        }
    }
</script>

<style scoped>

</style>
