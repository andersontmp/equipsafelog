import { createRouter, createWebHistory } from 'vue-router'
import EmployeeDetail from '../views/employee/EmployeeDetail.vue'
import LoginPage from '@/views/LoginPage.vue'
import App from '../App.vue'
const routes = [
  {
    path: '/',
    name: 'LoginPage',
    component: LoginPage,
    props: false
  },
  {
    path: '/App',
    name: 'Home',
    component: App
  },
  {
    path: '/employee/:id',
    name: 'employee',
    component: EmployeeDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
