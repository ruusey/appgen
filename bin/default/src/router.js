import Vue                      from 'vue'
import Router                   from 'vue-router'
import Home                     from './views/dashboard/Home.vue'
import Test                     from './views/dashboard/Test.vue'
import JobRequests              from './views/dashboard/JobRequests.vue'
import Clients                  from './views/dashboard/Clients.vue'
import ClientShow               from './views/dashboard/ClientShow.vue'
import ClientEdit               from './views/dashboard/ClientEdit.vue'
import ServiceProviders         from './views/dashboard/ServiceProviders.vue'
import ServiceProviderShow      from './views/dashboard/ServiceProviderShow.vue'
import ServiceProviderEdit      from './views/dashboard/ServiceProviderEdit.vue'
import store from "./store";

Vue.use(Router)

export default new Router({
  linkActiveClass: "active",
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/dashboard',
      component: () => import('./views/dashboard/layout.vue'),
      
      children: [
        {
          path: '/',
          name: 'dashboard',
          component: Home,
        },

        {
          path: '/dashboard/client',
          component: Clients,
        },
        {
          path: '/dashboard/client/:id',
          component: ClientShow,
        },
        {
          path: '/dashboard/client/new',
          component: ClientEdit,
        },
        {
          path: '/dashboard/client/:id/edit',
          component: ClientEdit,
        },
        {
          path: '/dashboard/serviceProvider',
          component: ServiceProviders,
        },
        {
          path: '/dashboard/serviceProvider/:id/edit',
          component: ServiceProviderEdit,
        },
        {
          path: '/dashboard/serviceProvider/:id',
          component: ServiceProviderShow,
        },
        {
          path: '/dashboard/serviceProvider/new',
          component: ServiceProviderEdit,
        },
        {
          path: '/dashboard/jobRequest',
          component: JobRequests,
        },
        {
          path: '/dashboard/test',
          component: Test,
        }
        
      ]
    }
  ]
})
