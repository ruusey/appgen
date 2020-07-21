<template>
    <div>
        <v-navigation-drawer app v-model="drawer">
            <v-list>
                <v-list-item-group color="primary">
    <!--                <router-link tag="div" to="/dashboard">-->
    <!--                    <v-list-item>-->
    <!--                        <v-list-item-action>-->
    <!--                            <v-icon>mdi-home</v-icon>-->
    <!--                        </v-list-item-action>-->
    <!--                        <v-list-item-content>-->
    <!--                            <v-list-item-title>Dashboard</v-list-item-title>-->
    <!--                        </v-list-item-content>-->
    <!--                    </v-list-item>-->
    <!--                </router-link>-->

                    <router-link tag="div" to="/dashboard/client" v-if="showLessees">
                        <v-list-item>
                            <v-list-item-action>
                                <v-icon>mdi-domain</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title>Clients</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </router-link>

                    <router-link tag="div" to="/dashboard/serviceProvider" v-if="showLessees">
                        <v-list-item>
                            <v-list-item-action>
                                <v-icon>mdi-domain</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title>ServiceProviders</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </router-link>

                </v-list-item-group>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar app dark>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
            <v-toolbar-title> JEM</v-toolbar-title>
            <img src="@/assets/gem.png" class="logo">
            <v-spacer></v-spacer>
            <div class="mr-2" @click="loadProfilePage">{{ user.email }}</div>
            <v-menu bottom left>
                <template v-slot:activator="{ on }">
                    <v-btn dark icon v-on="on">
                        <v-icon>mdi-account-circle</v-icon>
                    </v-btn>
                </template>

                <v-list>
                    <v-list-item-group color="primary">
                        <v-list-item @click="loadProfilePage">
                            <v-list-item-title>Profile</v-list-item-title>
                        </v-list-item>
                        <v-list-item @click="logoutUser">
                            <v-list-item-title>Logout</v-list-item-title>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-menu>
        </v-app-bar>

        <!-- Sizes your content based upon application components -->
        <v-content>

            <!-- Provides the application the proper gutter -->
            <v-container fluid>
                <router-view></router-view>
            </v-container>
        </v-content>

        <v-footer app>
            <!-- -->
        </v-footer>
    </div>
</template>

<script>
    import { mapState } from 'vuex';
    import getAbilities from '../../ability.js';

    export default {
        name: "layout",
        props: {
            source: String,
        },
        data: () => ({
            drawer: null,
        }),
        computed: {
            ...mapState(['user']),
            showLessees(){
                return getAbilities().can('view','lessees');
            },
            showReports(){
                return getAbilities().can('view','reports');
            },
            showTechnicianServiceHistory(){
                return getAbilities().can('view','technicianServiceHistory');
            },
            showSiteServiceHistory(){
                return getAbilities().can('view','siteServiceHistory');
            },
            showServiceProviders(){
                return getAbilities().can('view','serviceProviders');
            },
            showUsers(){
                return getAbilities().can('view','users');
            },
        },
        methods: {
            logoutUser(){
                this.$store.dispatch('AUTH_LOGOUT').then(() => {
                    this.$router.push({ name: 'login' });
                    console.log('user logged out.');
                }).catch((err) => {
                    console.log(err);
                })
            },
            loadProfilePage(){
                this.$router.push('/dashboard/profile');
            }
        }
    }
</script>

<style scoped lang="scss">
    .logo {
        max-height: 20px;
        margin-left: 10px;
        margin-right: 10px;
    }
</style>
