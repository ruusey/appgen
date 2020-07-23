<template>
    <div>
        <v-data-table :headers="headers"
                      :items="sps"
                      :search="search"
                      :loading="loading"
                      @click:row="loadShowRoute"
                      class="elevation-1 lessees-table">
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>Service Providers</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-text-field
                            v-model="search"
                            label="Search"
                            single-line
                            hide-details
                    ></v-text-field>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" dark class="mb-2" @click="loadCreateRoute">New ServiceProvider</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
            </template>
            <template v-slot:no-data>
                <v-btn color="primary" @click="getAllServiceProviders">Reset</v-btn>
            </template>
        </v-data-table>
        
    </div>

</template>

<script>
    import ServiceProviderService from '../../services/serviceProvider.js';
    export default {
        name: "ServiceProvider",
        data: () =>{
            return {
                loading: false,
                search: '',
                dialog: false,
                formTitle: 'ServiceProviders',
                headers: [
                    {
                        text: 'Email',
                        sortable: true,
                        value: 'email',
                    },
                     {
                        text: 'Username',
                        sortable: true,
                        value: 'userName',
                    },
                     {
                        text: 'First',
                        sortable: true,
                        value: 'firstName',
                    },
                     {
                        text: 'Last',
                        sortable: true,
                        value: 'lastName',
                    },
                    {
                        text: 'Rating',
                        sortable: true,
                        value: 'rating',
                    },
                    { text: 'Actions', value: 'action', sortable: false },
                ],
                sps: []
            }
        },
        methods: {
          getAllServiceProviders(){
              this.loading = true;
              ServiceProviderService.all().then(res => {
                  console.log(res);
                  this.sps = res;
              }).catch(err => {
                  console.log(err)
              }).finally(() => this.loading = false);
          },
          editItem(item){
              this.$router.push(`/dashboard/serviceProvider/${item.id}/edit`);
          },
          loadShowRoute(item){
              this.$router.push(`/dashboard/serviceProvider/${item.id}`);
          },
          loadCreateRoute(){
              this.$router.push('/dashboard/serviceProvider/new');
          },
        },
        mounted(){
            this.getAllServiceProviders();
        },
        
    }
</script>

<style scoped lang="scss">
    .lessees-table {
        max-width: 800px;
    }
</style>
