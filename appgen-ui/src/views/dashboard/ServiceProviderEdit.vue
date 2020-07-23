<template>
    <div>
        <v-card class="mx-auto p3"
                max-width="500"
                outlined>
            <v-toolbar dark flat>
                <v-toolbar-title v-if="this.sp.id">Edit Serivice Provider</v-toolbar-title>
                <v-toolbar-title v-if="!this.sp.id">New Serivice Provider</v-toolbar-title>
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
                <v-btn v-if="sp.id" color="warning" @click="deleteItem">Delete</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="saveItem">Save</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
    import States from '../../modules/usStates'
    import { mask } from 'vue-the-mask';
    import ServiceProviderService from '../../services/serviceProvider.js';
    export default {
        name: "ServiceProviderEdit",
        directives: { mask },
        data: () => {
            return {
                states: States,
                sp: {
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
                  ServiceProviderService.get(this.$route.params.id).then(res => {
                      console.log(res);
                      this.sp = res;
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
                  ServiceProviderService.update(this.sp).then(res => {
                      this.$store.dispatch('ADD_NOTIFICATION', {
                          text: 'Client Updated' ,
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
                          text: 'Client Created',
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
                        text: 'Client Deleted' ,
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
