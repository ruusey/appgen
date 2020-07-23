<template>
    <div>
        <v-card class="mx-auto p3"
                max-width="400"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title v-if="this.client.id">Edit Client</v-toolbar-title>
                <v-toolbar-title v-if="!this.client.id">New Client</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-form>
					<v-text-field v-if="this.client.id" label="Id" v-model="client.id" type="number"></v-text-field>
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
    import States from '../../modules/usStates'
    import { mask } from 'vue-the-mask';
    import ClientService from '../../services/client.js';
    export default {
        name: "ClientEdit",
        directives: { mask },
        data: () => {
            return {
                states: States,
                client: {
					id: 0,
                    email: '',
                    userName:'',
                    firstName:'',
                    lastName: '',
                    rating:0.0
                   
                },
                zipMask: '#####',
                phoneMask: '###-###-####'
            }
        },
        methods: {
            loadItem(){
              if (this.$route.params.id) {
                  ClientService.get(this.$route.params.id).then(res => {
                      console.log(res);
                      this.client = res;
                  }).catch(err => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: err.response.data.message ,
                          color: 'primary',
                      });
                  });
              }
            },
            saveItem(){
              if (this.$route.params.id) {
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
