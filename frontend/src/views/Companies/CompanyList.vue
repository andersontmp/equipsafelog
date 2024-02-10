<template lang="pug">
    div
      table
        thead
          tr
            th Razao Social
            th CNPJ
            th Telefone 
            th Responsavel
        tbody
          tr(v-for="item in data" :key="item.id")
            td {{ item.socialName }}
            td {{ item.cnpj }}
            td {{ item.phone }}
            td {{ item.responsable }}
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        data: [] // Armazenará os dados obtidos do endpoint
      };
    },
    mounted() {
      let that = this;
      axios.get("/api/equipsafelog/company") // Use o endereço do endpoint da configuração
        .then(response => {
          that.data = response.data;
        })
        .catch(error => {
          console.error('Erro ao obter os dados das empresas:', error);
        });
    }
  };
  </script>
  
  <style scoped>
  /* Estilos específicos para este componente, se necessário */
  </style>