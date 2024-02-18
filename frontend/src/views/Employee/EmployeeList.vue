<template lang="pug">
div(v-if="!isDetail")
  .d-flex.justify-content-start
    button.btn.btn-success.me-2(
      type="button",
      @click="createEmployee",
      title="Adicionar Funcionario"
    ) +
  .input-group
    span.custom-span.me-5 Pesquisa
    span.custom-span.me-2 Empresa
    select.custom-span.me-2(v-model="selectedCompany", @change="setCompany")
      option(key="", value="")
      option(v-for="item in companiesList", :key="item.id", :value="item.id") {{ item.socialName }}
    button.custom-span(@click="filterList") Buscar
  table.custom-table
    thead
      tr
        th Matricula
        th Ativo
        th Empresa
        th Detalhes
    tbody
      tr(
        v-for="item in getListData()",
        :key="item.id"
      )
        td {{ item.identity }}
        td {{ item.active }}
        td {{ item.company.socialName }}
        td
          button.btn.btn-warning.btn-sm(type="button" @click="viewDetails(item.id)" title="Editar") >
EmployeeDetail(
  v-if="isDetail",
  :id="employeeId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
</template>

<script>
import EmployeeDetail from "./EmployeeDetail.vue";
import EmployeeService from "@/components/services/EmployeeService";
import CompanyService from "@/components/services/CompanyService";

export default {
  components: {
    EmployeeDetail,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      employeeId: "",
      selectedCompany: "",
      isDetail: false,
      companiesList: [],
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
    viewDetails(employeeId) {
      this.employeeId = employeeId;
      this.isDetail = true;
    },
    createEmployee() {
      this.employeeId = "";
      this.isDetail = true;
    },
    closeDetail() {
      this.isDetail = false;
      this.populateEmployees();
      this.filterList();
    },
    cancelDetail() {
      this.isDetail = false;
    },
    populateEmployees() {
      let that = this;
      EmployeeService.getAllEmployees()
        .then((response) => {
          that.data = response;
          that.data.sort(this.sortByIdentity);
        })
        .catch((error) => {
          console.error("Erro ao obter os dados dos funcionários:", error);
        });
    },
    filterList() {
      let that = this;
      if (this.selectedCompany) {
        EmployeeService.getEmployeeByCompany(this.selectedCompany, true)
          .then((response) => {
            that.filteredData = response;
            that.filteredData.sort(this.sortByIdentity);
          })
          .catch((error) => {
            console.error("Erro ao obter os dados dos funcionários:", error);
          });
      } else {
        that.filteredData = undefined;
      }
    },
    sortByIdentity(a, b){
      const strA = a.identity.toUpperCase();
      const strB = b.identity.toUpperCase();

      if (strA < strB) {
        return -1;
      }
      if (strA > strB) {
          return 1;
      }
      return 0;
      }
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    this.populateEmployees();
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
</style>