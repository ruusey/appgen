<template>
    <div>
        <h2 class="pl-5 pa-2">{{ client.userName }}
            's Listings
        </h2>
        <v-skeleton-loader v-if="loadingLessee" class="pa-2" type="heading" max-width="500"></v-skeleton-loader>
        <v-card class="mx-auto p3"
                max-width="500"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title>Edit Service Provider</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <gmaps-map v-if="!loadingLessee" style="width: 450px; height: 400px" :options="mapOptions">
                    <gmaps-marker v-for="(job, i) in client.jobs" :key="i" :position="{ lat: job.loc.lat, lng: job.loc.lng}" />
                  
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
                max-width="400"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title v-if="client.id">Edit Client</v-toolbar-title>
                <v-toolbar-title v-if="!client.id">New Client</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-form>
					<v-text-field label="Id" v-model="client.id" type="number"></v-text-field>
                    <v-text-field label="Email" v-model="client.email" type="text"></v-text-field>
                    <v-text-field label="User Name" v-model="client.userName" type="text"></v-text-field>
                    <v-text-field label="First Name" v-model="client.firstName" type="text"></v-text-field>
                     <v-text-field label="Last Name" v-model="client.lastName" type="text"></v-text-field>
                    <v-text-field label="Rating" v-model="client.rating" type="number"></v-text-field>
                   
                   
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
    import ClientService from '../../services/client.js';
     import { gmapsMap, gmapsMarker } from 'x5-gmaps'
    export default {
        name: "ClientShow",
        components: { gmapsMap, gmapsMarker },
        data: () => {
            return {
                client: {
					id: 0,
                    email: '',
                    userName:'',
                    firstName:'',
                    lastName: '',
                    rating:0.0
                   
                },
                mapOptions: {},
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
                
               
            }
        },
        methods: {
            loadItem(){
                if (this.$route.params.id!=='new') {
                  ClientService.get(this.$route.params.id).then(res => {
                      console.log(res);
                      this.client = res;
                      this.loadingSites=false;
                      this.loadingLessee=false;
                      this.mapOptions={ center: { lat: res.loc.lat, lng: res.loc.lng }, zoom: 9}
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary',
                      });
                  });
              }else{
                  this.loadingSites=false;
                  this.loadingLessee=false;
              }
            },
            saveItem(){
              if (this.$route.params.id!=='new') {
                  ClientService.update(this.client).then(res => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: 'Client Updated' ,
                          color: 'success'
                      });
                      this.$router.push('/dashboard/client');
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary'
                      })
                  })
              } else {
                  ClientService.create(this.client).then(res => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: 'Client Created',
                          color: 'success'
                      });
                      this.$router.push('/dashboard/client');
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary'
                      })
                  })
              }
            },
            deleteItem(){
                ClientService.destroy(this.client).then(res => {
                    this.$store.dispatch('ADD_NOTIFICATION', {
                        text: 'Client Deleted' ,
                        color: 'primary'
                    });
                    this.$router.push('/dashboard/client');
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
