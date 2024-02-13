import { createRouter, createWebHistory } from 'vue-router'
import EmployeeDetail from '../views/employee/EmployeeDetail.vue'

const routes = [
   {
    path: '/employee/:id',
    name: 'EmployeeDetail',
    component: EmployeeDetail,
    props: true // Permite passar props para o componente de detalhes
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
