<template lang="pug">
  div(v-if="!isDetail")
    .d-flex.justify-content-start
      button.btn.btn-success(type="button" @click="createEmployee" title="Adicionar Funcionario") +
    table.custom-table
      thead
        tr
          th Matricula
          th Ativo
      tbody
        tr(v-for="item in data" :key="item.id" @click="viewDetails(item.id)")
          td {{ item.identity }}
          td {{ item.active }}
  EmployeeDetail(v-if="isDetail" :id="employeeId" @closeDetail="closeDetail" @cancelDetail="cancelDetail")
</template>

<script>
import EmployeeDetail from './EmployeeDetail.vue';
import EmployeeService from '@/components/services/EmployeeService';

export default {
  components: {
    EmployeeDetail
  },
  data() {
    return {
      data: [],
      employeeId:'',
      isDetail:false
    };
  },
  methods: {
    viewDetails(employeeId) {
      //this.$router.push({ name: 'employee', params: { id: employeeId } });
      console.info(employeeId);
      this.employeeId = employeeId;
      this.isDetail = true;
    },
    createEmployee(){
      this.employeeId = '';
      this.isDetail = true;
    },
    closeDetail(){
      this.isDetail = false;
      this.populateEmployees();
    },
    cancelDetail(){
      this.isDetail = false;
    },
    populateEmployees(){
      let that = this;
      EmployeeService.getAllEmployees().then(response => {
        that.data = response;
        that.data.sort((a,b) => a.identity - b.identity);
      })
      .catch(error => {
        console.error('Erro ao obter os dados dos funcionários:', error);
      });
    }
  },
  mounted() {
    this.populateEmployees();
  }
};
</script>

<style scoped>
.table-container {
  overflow-x: auto; /* Adiciona uma barra de rolagem horizontal se a tabela for muito larga */
}

.custom-table {
  margin-top: 5px;
  width: 100%;
  border-collapse: collapse;
}

.custom-table th, .custom-table td {
  border: 1px solid #ddd; /* Define as bordas */
  padding: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}
</style>