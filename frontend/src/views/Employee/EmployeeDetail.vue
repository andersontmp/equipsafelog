<template>
  <div>
    <h1>Detalhes do Funcionário {{ employee.id }}</h1>
    <p>Matricula: {{ employee.identity }}</p>
    <p>Ativo: {{ employee.active }}</p>
  </div>
</template>
  
  <script>
import axios from "axios";

export default {
  props: ["id"],
  data() {
    return {
      employee: null,
    };
  },
  created() {
    let that = this;
    axios
      .get("/api/equipsafelog/employee/" + this.id) // Use o endereço do endpoint da configuração
      .then((response) => {
        that.employee = response.data;
      })
      .catch((error) => {
        console.error("Erro ao obter os dados dos funcionários:", error);
      });
  },
};
</script>
  
  <style>
/* Estilos, se necessário */
</style>
  