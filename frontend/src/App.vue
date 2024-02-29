<template lang="pug">
  div.container
    span.text-center Equip Safe Log
  div.container
    ul.menu
      li(@click="selectOption('Empregados')" :class="{ active: selectedOption === 'Empregados' }") Empregados
      li(@click="selectOption('Empresa')" :class="{ active: selectedOption === 'Empresa' }") Empresa
      li(@click="selectOption('Setor')" :class="{ active: selectedOption === 'Setor' }") Setor
      li(@click="selectOption('Terminal')" :class="{ active: selectedOption === 'Terminal' }") Terminal
      li(@click="selectOption('Controle de Uso')" :class="{ active: selectedOption === 'Controle de Uso' }") Controle de Usuario
      li(@click="selectOption('Perfil')" :class="{ active: selectedOption === 'Perfil' }") Perfil
      li(@click="selectOption('Registro de Utilização')" :class="{ active: selectedOption === 'Registro de Utilização' }") Registro de Utilização

  // Aqui você pode adicionar o conteúdo correspondente à opção selecionada
  div(v-if="selectedOption === 'Empregados'") 
    EmployeeListVue()
  div(v-else-if="selectedOption === 'Empresa'")
    CompanyListVue() 
  div(v-else-if="selectedOption === 'Setor'")
    SectorListVue() 
  div(v-else-if="selectedOption === 'Terminal'") 
    TerminalListVue()
  div(v-else-if="selectedOption === 'Controle de Uso'")
    UserListVue()
  div(v-else-if="selectedOption === 'Perfil'") Conteúdo da opção Perfil
  div(v-else-if="selectedOption === 'Registro de Utilização'")
    PointRegisterListVue()
    
</template>
<script>
import CompanyListVue from './views/companies/CompanyList.vue';
import EmployeeListVue from './views/employee/EmployeeList.vue';
import TerminalListVue from './views/terminal/TerminalList.vue';
import UserListVue from './views/user/UserList.vue';
import PerfilListVue from './views/perfil/PerfilList.vue';
import PointRegisterListVue from './views/pointRegister/PointRegisterList.vue';
import SectorListVue from './views/sector/SectorList.vue';

export default {
  components: {
    CompanyListVue,
    EmployeeListVue,
    TerminalListVue,
    UserListVue,
    PerfilListVue,
    PointRegisterListVue,
    SectorListVue
  },
  data() {
    return {
      selectedOption: null
    };
  },
  methods: {
    selectOption(option) {
      this.selectedOption = option;
    }
  }
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
}

.text-center {
  display: inline-block;
  font-size: x-large;
  font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
}
</style>