<template lang="pug">
.container
  span.text-center Equip Safe Log
.container
  Menubar(:model="items")
div(v-if="selectedOption === 'Empregados'") 
  |
  EmployeeListVue(:isDetailProp="createdNew")
div(v-else-if="selectedOption === 'Empresa'")
  CompanyListVue(:isDetailProp="createdNew") 
div(v-else-if="selectedOption === 'Setor'")
  SectorListVue(:isDetailProp="createdNew") 
div(v-else-if="selectedOption === 'Terminal'") 
  |
  TerminalListVue(:isDetailProp="createdNew")
div(v-else-if="selectedOption === 'Controle de Uso'")
  UserListVue(:isDetailProp="createdNew")
div(v-else-if="selectedOption === 'Perfil'") Conteúdo da opção Perfil
div(v-else-if="selectedOption === 'Registro de Utilização'")
  PointRegisterListVue
Toast(position="top-right")
</template>
<script>
import CompanyListVue from "./company/CompanyList.vue";
import EmployeeListVue from "./employee/EmployeeList.vue";
import TerminalListVue from "./terminal/TerminalList.vue";
import UserListVue from "./user/UserList.vue";
import PerfilListVue from "./perfil/PerfilList.vue";
import PointRegisterListVue from "./pointRegister/PointRegisterList.vue";
import SectorListVue from "./sector/SectorList.vue";
import Menubar from "primevue/menubar";
import Toast from 'primevue/toast';

export default {
  components: {
    CompanyListVue,
    EmployeeListVue,
    TerminalListVue,
    UserListVue,
    PerfilListVue,
    PointRegisterListVue,
    SectorListVue,
    Menubar,
    Toast,
  },
  data() {
    return {
      selectedOption: null,
      createdNew: false,
      items: [
        {
          label: "Colaborador",
          icon: "pi pi-user",
          items: [
            {
              label: "Criar",
              icon: "pi pi-plus",
              command: () => {
                this.createdNew = true;
                this.selectedOption = "Empregados";
              },
            },
            {
              label: "Listar",
              icon: "pi pi-list",
              command: () => {
                this.createdNew = false;
                this.selectedOption = "Empregados";
              },
            },
          ],
        },
        {
          label: "Empresa",
          icon: "pi pi-building",
          items: [
            {
              label: "Criar",
              icon: "pi pi-plus",
              disabled: !this.isAdmin(),
              command: () => {
                this.createdNew = true;
                this.selectedOption = "Empresa";
              },
            },
            {
              label: "Listar",
              icon: "pi pi-list",
              command: () => {
                this.createdNew = false;
                this.selectedOption = "Empresa";
              },
            },
          ],
        },
        {
          label: "Setor",
          icon: "pi pi-sitemap",
          items: [
            {
              label: "Criar",
              icon: "pi pi-plus",
              command: () => {
                this.createdNew = true;
                this.selectedOption = "Setor";
              },
            },
            {
              label: "Listar",
              icon: "pi pi-list",
              command: () => {
                this.createdNew = false;
                this.selectedOption = "Setor";
              },
            },
          ],
        },
        {
          label: "Terminal",
          icon: "pi pi-stop",
          items: [
            {
              label: "Criar",
              icon: "pi pi-plus",
              disabled: !this.isAdmin(),
              command: () => {
                this.createdNew = true;
                this.selectedOption = "Terminal";
              },
            },
            {
              label: "Listar",
              icon: "pi pi-list",
              command: () => {
                this.createdNew = false;
                this.selectedOption = "Terminal";
              },
            },
          ],
        },
        {
          label: "Registro de Utilização",
          icon: "pi pi-calendar",
          items: [
            {
              label: "Listar",
              icon: "pi pi-search",
              command: () => {
                this.createdNew = false;
                this.selectedOption = "Registro de Utilização";
              },
            },
          ],
        },
        {
          label: "Sair",
          icon: "pi pi-sign-out",
          command: () => {
            this.$store.dispatch("saveData", {
              token: undefined,
              user: undefined,
              pass: undefined,
              role: undefined,
            });
            this.$router.push({ name: "Login" });
          },
        },
      ],
    };
  },
  methods: {
    isAdmin(){
      return this.$store.getters.getData?.role == "ADMIN";
    }
  },
  mounted() {
    let token = this.$store.getters.getData?.token;
    if (!token) {
      this.$router.push({ name: "Login" });
    }else{
      this.$toast.add({ severity: 'success', summary: 'Bem Vindo', detail: 'Bem Vindo ao Equip Safe Log', life: 3000 });
    }
  },
};
</script>
<style scoped>
.menu {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu li {
  display: inline-block;
  padding: 10px 20px;
  cursor: pointer;
}

.menu li.active {
  background-color: #007bff;
  color: white;
}

.menu li:hover {
  background-color: #f0f0f0;
}
.container {
  text-align: center;
  margin-bottom: 10px;
  color: white;
}

.text-center {
  display: inline-block;
  font-size: x-large;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
</style>