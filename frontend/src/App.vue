<template lang="pug">
  div
    ul.menu
      li(@click="selectOption('Empregados')" :class="{ active: selectedOption === 'Empregados' }") Empregados
      li(@click="selectOption('Empresa')" :class="{ active: selectedOption === 'Empresa' }") Empresa
      li(@click="selectOption('Conta')" :class="{ active: selectedOption === 'Conta' }") Conta
      li(@click="selectOption('Terminal')" :class="{ active: selectedOption === 'Terminal' }") Terminal
      li(@click="selectOption('Controle de Uso')" :class="{ active: selectedOption === 'Controle de Uso' }") Controle de Uso
      li(@click="selectOption('Perfil')" :class="{ active: selectedOption === 'Perfil' }") Perfil
      li(@click="selectOption('Registro de Utilização')" :class="{ active: selectedOption === 'Registro de Utilização' }") Registro de Utilização

  // Aqui você pode adicionar o conteúdo correspondente à opção selecionada
  div(v-if="selectedOption === 'Empregados'") 
    EmployeeListVue()
  div(v-else-if="selectedOption === 'Empresa'")
    CompanyListVue() 
  div(v-else-if="selectedOption === 'Conta'") Conteúdo da opção Conta
  div(v-else-if="selectedOption === 'Terminal'") 
    TerminalListVue()
  div(v-else-if="selectedOption === 'Controle de Uso'")
    UseControlListVue()
  div(v-else-if="selectedOption === 'Perfil'") 
    PerfilListVue()
  div(v-else-if="selectedOption === 'Registro de Utilização'")
    PointRegisterListVue()
    
</template>
<script>
import CompanyListVue from './views/companies/CompanyList.vue';
import EmployeeListVue from './views/employee/EmployeeList.vue';
import TerminalListVue from './views/terminal/TerminalList.vue';
import UseControlListVue from './views/useControl/UseControlList.vue';
import PerfilListVue from './views/perfil/PerfilList.vue';
import PointRegisterListVue from './views/pointRegister/PointRegisterList.vue';

export default {
  components: {
    CompanyListVue,
    EmployeeListVue,
    TerminalListVue,
    UseControlListVue,
    PerfilListVue,
    PointRegisterListVue,
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
</style>