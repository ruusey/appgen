<template>
    <div>
        <h2 class="pl-5 pa-2">{{ sp.userName }}</h2>
        <v-skeleton-loader v-if="loading" class="pa-2" type="heading" max-width="500"></v-skeleton-loader>
        <v-card class="mx-auto p3"
                max-width="500"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title>Edit Service Provider</v-toolbar-title>
                <v-toolbar-title v-if="!sp.id">New Service Provider</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <gmaps-map v-if="!loading" style="width: 450px; height: 400px" :options="mapOptions">
                    <gmaps-marker :position="{ lat: this.sp.loc.lat, lng: this.sp.loc.lng }" />
                </gmaps-map>
            </v-card-text>
            <v-card-actions>
                <v-btn color="default" @click="back">Cancel</v-btn>
                <v-spacer></v-spacer>
                <v-btn v-if="this.$route.params.id" color="warning" @click="deleteItem">Delete</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="saveItem">Save</v-btn>
            </v-card-actions>
        </v-card>    
        
           <v-card class="mx-auto p3"
                max-width="500"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title v-if="sp.id">Edit Service Provider</v-toolbar-title>
                <v-toolbar-title v-if="!sp.id">New Service Provider</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-form>
					<v-text-field label="Id" v-model="sp.id" type="number"></v-text-field>
                    <v-text-field label="Email" v-model="sp.email" type="text"></v-text-field>
                    <v-text-field label="User Name" v-model="sp.userName" type="text"></v-text-field>
                    <v-text-field label="First Name" v-model="sp.firstName" type="text"></v-text-field>
                     <v-text-field label="Last Name" v-model="sp.lastName" type="text"></v-text-field>
                    <v-text-field label="Rating" v-model="sp.rating" type="number"></v-text-field>
                   
                   
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn color="default" @click="back">Cancel</v-btn>
                <v-spacer></v-spacer>
                <v-btn v-if="this.$route.params.id" color="warning" @click="deleteItem">Delete</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="saveItem">Save</v-btn>
            </v-card-actions>
        </v-card>    
    </div>

</template>

<script>
    import ServiceProviderService from '../../services/serviceProvider.js';
    import { gmapsMap, gmapsMarker } from 'x5-gmaps'
    export default {
        name: "ServiceProviderShow",
        components: { gmapsMap, gmapsMarker },
        data: () => {
            return {
                mapOptions: {},
                sp: {},
                geoloc: {},
                
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
               
            }
        },
        methods: {
            loadItem(){
                this.loading = true;
                if (this.$route.params.id!=='new') {
                     ServiceProviderService.get(this.$route.params.id).then(res => {
                    console.log(res);
                    this.sp = res;

                    this.mapOptions={ center: { lat: res.loc.lat, lng: res.loc.lng }, zoom: 9}
                    
                    
                }).catch(err => {
                    console.log(err)
                }).finally(() => this.loading = false);
                }
               
            },
            saveItem(){
              if (this.$route.params.id!=='new') {
                  ServiceProviderService.update(this.sp).then(res => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: 'Service Provider Updated' ,
                          color: 'success'
                      });
                      this.$router.push('/dashboard/serviceProvider');
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary'
                      })
                  })
              } else {
                  ServiceProviderService.create(this.sp).then(res => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: 'Service Provider Created',
                          color: 'success'
                      });
                      this.$router.push('/dashboard/serviceProvider');
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary'
                      })
                  })
              }
            },
            deleteItem(){
                ServiceProviderService.destroy(this.sp).then(res => {
                    this.$store.dispatch('ADD_NOTIFICATION', {
                        text: 'Service Provider' ,
                        color: 'primary'
                    });
                    this.$router.push('/dashboard/serviceProvider');
                }).catch(err => {
                    this.$store.dispatch('ADD_NOTIFICATION', {
                        text: err.response.data.message,
                        color: 'success'
                    });
                })
            },
            back(){
              this.$router.back();
            }
        },
        mounted(){
            this.loadItem();
            
        }
    }
       
        
</script>

<style scoped>

</style>
