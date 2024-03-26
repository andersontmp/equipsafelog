import './assets/main.css'

import { createApp, h } from 'vue';
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'

import 'primevue/resources/themes/lara-dark-teal/theme.css'

import PrimeVue from 'primevue/config'

import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Home from './views/Home.vue'
import LoginPage from './views/LoginPage.vue'
import store from './store';
import ToastService from 'primevue/toastservice';


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        { path: '/', name:'Home', component: Home },
        { path: '/login', name:'Login', component: LoginPage },
      ],
    verbose: true // Ativar modo verbose
  })

const app = createApp({
    render: () => h(App), // Use a função h dentro da função de renderização
});

app.use(PrimeVue, { ripple: true })
app.use(router);
app.use(store);
app.use(ToastService);

app.component('Button', Button)
app.component('InputText', InputText)
app.mount('#app')
