<template>
    <div>
        <v-col :cols="6">
            <v-data-table :headers="headers"
                      :items="jr"
                      :search="search"
                      :loading="loading"
                      @click:row="loadShowRoute"
                      class="elevation-1 lessees-table">
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>Lease Operators</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-text-field
                            v-model="search"
                            label="Search"
                            single-line
                            hide-details
                    ></v-text-field>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" dark class="mb-2" @click="loadCreateRoute">New Lease Operator</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
            </template>
            <template v-slot:no-data>
                <v-btn color="primary" @click="getAllJobs">Reset</v-btn>
            </template>
        </v-data-table>
        </v-col>
         <v-col :cols="6">
             
           <template>
                <gmaps-map style="width:300px;height:500px;">
                   <gmaps-marker v-for="(item, i) in jrLoc" :key="i" :options="item.options" />
                </gmaps-map>
            </template>
            
        </v-col>
        
       
    </div>
    
</template>

<script>
    import JobRequestService from '../../services/jobRequest.js';
    import { gmapsMap, gmapsMarker } from 'x5-gmaps'
    export default {
        name: "JobRequest",
        components: { gmapsMap, gmapsMarker },
        data: () =>{
            return {
                loading: false,
                search: '',
                dialog: false,
                formTitle: 'JobRequests',
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
                    },
                    { text: 'Actions', value: 'action', sortable: false },
                ],
                jr: [],
                jrLoc:[]
            }
        },
        methods: {
          getAllJobs(){
              this.loading = true;
              JobRequestService.all().then(res => {
                  console.log(res[0].loc);
                 var loc = [];
                 res.forEach(function(item){
                    loc.push({options:{ position: { lat: item.loc.lat, lng: item.loc.lng }}})
                 });
                
                  this.jr = res;
                  this.jrLoc =loc;
                  
              }).catch(err => {
                  console.log(err)
              }).finally(() => this.loading = false);
          },
          editItem(item){
              this.$router.push(`/dashboard/jobRequest/${item.id}/edit`);
          },
          loadShowRoute(item){
              this.$router.push(`/dashboard/jobRequest/${item.id}`);
          },
          loadCreateRoute(){
              this.$router.push('/dashboard/jobRequest/new');
          },
        },
        mounted(){
            this.getAllJobs();
        }
    }
</script>

<style scoped lang="scss">
    .lessees-table {
        max-width: 800px;
    }
</style>
