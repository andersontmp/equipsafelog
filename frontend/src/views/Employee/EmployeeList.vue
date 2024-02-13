<template lang="pug">
  div
    table.custom-table
      thead
        tr
          th Matricula
          th Ativo
      tbody
        tr(v-for="item in data" :key="item.id" @click="viewDetails(item.id)")
          td {{ item.identity }}
          td {{ item.active }}
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      data: [] // Armazenará os dados obtidos do endpoint
    };
  },
  methods: {
    viewDetails(employeeId) {
      this.$router.push({ name: 'EmployeeDetail', params: { id: employeeId } });
    }
  },
  mounted() {
    let that = this;
    axios.get("/api/equipsafelog/employee") // Use o endereço do endpoint da configuração
      .then(response => {
        that.data = response.data;
      })
      .catch(error => {
        console.error('Erro ao obter os dados dos funcionários:', error);
      });
  }
};
</script>

<style scoped>
.table-container {
  overflow-x: auto; /* Adiciona uma barra de rolagem horizontal se a tabela for muito larga */
}

.custom-table {
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