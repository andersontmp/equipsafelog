<template lang="pug">
div
  table.custom-table
    thead
      tr
        th Usuario
        th Nome
        th Contato
    tbody
      tr(
        v-for="item in getListData()",
        :key="item.id",
        @click="viewDetails(item.id)"
      )
        td {{ item.username }}
        td {{ item.name }}
        td {{ item.contact }}
</template>

<script>
import UserService from "@/components/services/UserService";

export default {
  data() {
    return {
      data: [],
      filteredData: undefined,
    };
  },
  methods: {
    viewDetails(employeeId) {
      //this.$router.push({ name: "EmployeeDetail", params: { id: employeeId } });
      console.info(employeeId);
    },
    getListData() {
      if (this.filteredData) {
        return this.filteredData;
      } else {
        return this.data;
      }
    },
  },
  mounted() {
    let that = this;
    UserService.getAllUsers()
      .then((response) => {
        that.data = response;
      })
      .catch((error) => {
        console.error("Erro ao obter os dados dos usuarios:", error);
      });
  },
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