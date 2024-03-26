import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import LoginPage from './views/LoginPage.vue'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: Home },
    { path: '/login', component: LoginPage },
  ],
})
