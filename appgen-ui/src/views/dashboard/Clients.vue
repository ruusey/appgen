<template>
    <div>
        <v-data-table :headers="headers"
                      :items="clients"
                      :search="search"
                      :loading="loading"
                      @click:row="loadShowRoute"
                      class="elevation-1 lessees-table">
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>Clients</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-text-field
                            v-model="search"
                            label="Search"
                            single-line
                            hide-details
                    ></v-text-field>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" dark class="mb-2" @click="loadCreateRoute">New Client</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
            </template>
            <template v-slot:no-data>
                <v-btn color="primary" @click="getAllLessees">Reset</v-btn>
            </template>
        </v-data-table>
    </div>

</template>

<script>
    import ClientService from '../../services/client.js';
    export default {
        name: "Clients",
        data: () =>{
            return {
                loading: false,
                search: '',
                dialog: false,
                formTitle: 'Clients',
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
                clients: []
            }
        },
        methods: {
          getAllLessees(){
              this.loading = true;
              ClientService.all().then(res => {
                  console.log(res);
                  this.clients = res;
              }).catch(err => {
                  console.log(err)
              }).finally(() => this.loading = false);
          },
          editItem(item){
              this.$router.push(`/dashboard/client/${item.id}/edit`);
          },
          loadShowRoute(item){
              this.$router.push(`/dashboard/client/${item.id}`);
          },
          loadCreateRoute(){
              this.$router.push('/dashboard/client/new');
          },
        },
        mounted(){
            this.getAllLessees();
        }
    }
</script>

<style scoped lang="scss">
    .lessees-table {
        max-width: 800px;
    }
</style>
