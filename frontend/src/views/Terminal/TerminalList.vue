<template lang="pug">
  div
    table
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
/* Estilos específicos para este componente, se necessário */
</style>