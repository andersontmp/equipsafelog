<template lang="pug">
div
  span.custom-span Selecione a empresa
  select.custom-span(v-model="selectedCompany" @change="setCompany")
    option(key="", value="")
    option(v-for="item in companiesList", :key="item.id", :value="item.id") {{ item.socialName }}
  button(@click="filterList") Buscar
  table.custom-table
    thead
      tr
        th Matricula
        th Ativo
        th Ultima Comunicação
        th Empresa
    tbody
      tr(v-for="item in getListData()", :key="item.id", @click="viewDetails(item.id)")
        td {{ item.identity }}
        td {{ item.active }}
        td {{ formatDateTime(item.lastCommunication) }}
        td {{ item.company.socialName }}
</template>

<script>
import CompanyService from "@/components/services/CompanyService";
import TerminalService from "@/components/services/TerminalService";
import DateUtil from "@/components/utils/DateUtil";

export default {
  data() {
    return {
      data: [],
      filteredData: undefined,
      selectedCompany: "",
      companiesList: [],
    };
  },
  methods: {
    viewDetails(terminalId) {
      //this.$router.push({ name: "EmployeeDetail", params: { id: employeeId } });
      console.log(terminalId)
    },
    getListData() {
      if (this.filteredData) {
        return this.filteredData;
      } else {
        return this.data;
      }
    },
    filterList() {
      let that = this;
      if (this.selectedCompany) {
        TerminalService.getTerminalsByCompany(this.selectedCompany).then((response) => {
          if (response) {
            that.filteredData = response;
          }
        });
      }else{
        that.filteredData = undefined;
      }
    },
    setCompany(){
      console.log(this.selectedCompany);
    },
    formatDateTime(input){
      return DateUtil.formatDateTime(input)
    }
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    TerminalService.getAllTerminals().then((response) => {
      if (response) {
        that.data = response;
      }
    });
  },
};
</script>

<style scoped>
.custom-span {
  margin-right: 20px;
  margin-bottom: 10px;
}

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