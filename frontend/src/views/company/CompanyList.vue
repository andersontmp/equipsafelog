<template lang="pug">
.title_container
  span.text-center {{ title }}
div(v-if="!isDetail")
  DataTable(
    :value="data",
    stripedRows,
    size="small",
    paginator,
    :rows="10",
    :rowsPerPageOptions="[10, 25, 50, 100]",
    tableStyle="min-width: 50rem"
  )
    Column(field="socialName", header="Razao Social")
    Column(field="cnpj", header="CNPJ")
    Column(field="phone", header="Telefone")
    Column(field="responsable", header="Responsavel")
    Column(header="Detalhes")
      template(#body="slotProps")
        Button(
          @click="viewDetails(slotProps.data)",
          text,
          rounded,
          outlined,
          severity="info",
          size="small"
        )
          i.pi.pi-search
CompanyDetail(
  v-if="isDetail",
  :id="companyId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
Toast(position="top-right")
</template>
  
  <script>
import CompanyService from "@/components/services/CompanyService";
import CompanyDetail from "./CompanyDetail.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Toast from "primevue/toast";

export default {
  name: "CompanyList",
  props: {
    isDetailProp: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    isDetailProp() {
      this.isDetail = this.isDetailProp;
    },
    isDetail() {
      this.title = this.isDetail ? "Empresa" : "Empresas";
    },
  },
  components: {
    CompanyDetail,
    DataTable,
    Column,
    Toast,
  },
  data() {
    return {
      data: [],
      companyId: "",
      isDetail: false,
      title: "Empresas",
    };
  },
  methods: {
    viewDetails(companyId) {
      //this.$router.push({ name: 'employee', params: { id: employeeId } });
      console.info(companyId);
      this.companyId = companyId.id;
      this.isDetail = true;
    },
    createCompany() {
      this.companyId = undefined;
      this.isDetail = true;
    },
    closeDetail() {
      this.$toast.add({
        severity: "success",
        summary: "Empresa",
        detail: "Registro salvo com sucesso",
        life: 3000,
      });
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
        })
        .catch((error) => {
          console.error("Erro ao obter os dados das empresas:", error);
        });
    },
  },
  mounted() {
    this.populateCompanies();
    if (this.isDetailProp) {
      this.isDetail = this.isDetailProp;
    }
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
  padding-left: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}
.margin-bottom {
  margin-bottom: 10px;
}
.title_container {
  text-align: center;
}
.text-center {
  display: inline-block;
  font-size: large;
  padding-bottom: 10px;
  color: white;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
</style>