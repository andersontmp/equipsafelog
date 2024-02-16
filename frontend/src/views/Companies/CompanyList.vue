<template lang="pug">
div(v-if="!isDetail")
  .d-flex.justify-content-start
    button.btn.btn-success(type="button" @click="createCompany" title="Adicionar Empresa") +
  table.custom-table
    thead
      tr
        th Razao Social
        th CNPJ
        th Telefone
        th Responsavel
    tbody
      tr(v-for="item in data", :key="item.id", @click="viewDetails(item.id)")
        td {{ item.socialName }}
        td {{ item.cnpj }}
        td {{ item.phone }}
        td {{ item.responsable }}
CompanyDetail(
  v-if="isDetail",
  :id="companyId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
</template>
  
  <script>
import CompanyService from "@/components/services/CompanyService";
import CompanyDetail from "./CompanyDetail.vue";
export default {
  components: {
    CompanyDetail,
  },
  data() {
    return {
      data: [],
      companyId: "",
      isDetail: false,
    };
  },
  methods: {
    viewDetails(companyId) {
      //this.$router.push({ name: 'employee', params: { id: employeeId } });
      console.info(companyId);
      this.companyId = companyId;
      this.isDetail = true;
    },
    createCompany(){
      this.companyId = '';
      this.isDetail = true;
    },
    closeDetail() {
      this.isDetail = false;
      this.populateCompanies();
    },
    cancelDetail() {
      this.isDetail = false;
    },
    populateCompanies() {
      let that = this;
      CompanyService.getAllCompanies()
        .then((response) => {
          that.data = response;
          that.data.sort((a,b) => a.socialName - b.socialName);
        })
        .catch((error) => {
          console.error("Erro ao obter os dados das empresas:", error);
        });
    },
  },
  mounted() {
    this.populateCompanies();
  },
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

.custom-table th,
.custom-table td {
  border: 1px solid #ddd; /* Define as bordas */
  padding: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}
</style>