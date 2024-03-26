<template lang="pug">
.title_container
  span.text-center {{ title }}
div(v-if="!isDetail")
  .input-group
    span.custom-span Pesquisa
    span.custom-span.me-2 Empresa
    Dropdown(
      v-model="selectedCompany",
      :options="companiesList",
      optionLabel="socialName",
      placeholder="Selecione uma empresa",
      @change="setCompany",
      showClear
    )
    Button.margin-button(@click="filterList") Buscar
  span.custom-span 
  DataTable(
    :value="getListData()",
    stripedRows,
    size="small",
    paginator,
    :rows="10",
    :rowsPerPageOptions="[10, 25, 50, 100]",
    tableStyle="min-width: 50rem"
  )
    Column(header="Matricula")
      template(#body="slotProps")
        span {{ slotProps.data.identity }} - {{ slotProps.data.name }}
    Column(header="Ativo")
      template(#body="slotProps")
        i.pi.pi-check-circle(
          v-if="slotProps.data.active",
          style="color: green"
        )
        i.pi.pi-times-circle(v-if="!slotProps.data.active", style="color: red")
    Column(field="sector.company.socialName", header="Empresa")
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
EmployeeDetail(
  v-if="isDetail",
  :id="employeeId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
Toast(position="top-right")
</template>

<script>
import EmployeeDetail from "./EmployeeDetail.vue";
import EmployeeService from "@/components/services/EmployeeService";
import CompanyService from "@/components/services/CompanyService";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Dropdown from "primevue/dropdown";
import "primevue/resources/primevue.min.css";
import "primeicons/primeicons.css";
import Toast from "primevue/toast";

export default {
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
      this.title = this.isDetail ? "Colaborador" : "Colaboradores";
    },
  },
  components: {
    EmployeeDetail,
    DataTable,
    Column,
    Dropdown,
    Toast,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      employeeId: undefined,
      selectedCompany: undefined,
      companiesList: [],
      isDetail: false,
      title: "Colaboradores",
    };
  },
  methods: {
    getListData() {
      if (this.filteredData) {
        return this.filteredData;
      } else {
        return this.data;
      }
    },
    viewDetails(employee) {
      this.employeeId = employee.id;
      this.isDetail = true;
    },
    createEmployee() {
      this.employeeId = "";
      this.isDetail = true;
    },
    closeDetail() {
      this.$toast.add({
        severity: "success",
        summary: "Colaborador",
        detail: "Registro salvo com sucesso",
        life: 3000,
      });
      this.isDetail = false;
      this.filterList();
    },
    cancelDetail() {
      this.isDetail = false;
    },
    filterList() {
      let that = this;
      if (this.selectedCompany) {
        EmployeeService.getEmployeeByCompany(this.selectedCompany.id, true)
          .then((response) => {
            that.filteredData = response;
          })
          .catch((error) => {
            console.error("Erro ao obter os dados dos funcionários:", error);
          });
      } else {
        that.filteredData = undefined;
      }
    },
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    if (this.isDetailProp) {
      this.isDetail = this.isDetailProp;
    }
  },
};
</script>

<style scoped>
.title_container {
  text-align: center;
}
.table-container {
  overflow-x: auto;
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

.margin-button {
  margin-left: 10px;
}

.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}
.small-btn {
  font-size: 5px;
}
.text-center {
  display: inline-block;
  font-size: large;
  color: white;
  padding-bottom: 10px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
</style>