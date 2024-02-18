<template lang="pug">
div(v-if="!isDetail")
  .d-flex.justify-content-start
    button.btn.btn-success(type="button" @click="createTerminal" title="Adicionar Terminal") +
  .input-group
    span.custom-span.me-5 Pesquisa
    span.custom-span.me-2 Empresa
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
        th Detalhes
    tbody
      tr(v-for="item in getListData()", :key="item.id")
        td {{ item.identity }}
        td {{ item.active }}
        td {{ formatDateTime(item.lastCommunication) }}
        td {{ item.company.socialName }}
        td
          button.btn.btn-warning.btn-sm(type="button" @click="viewDetails(item.id)" title="Editar") >
TerminalDetailVue(
  v-if="isDetail",
  :id="terminalId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail")
</template>

<script>
import CompanyService from "@/components/services/CompanyService";
import TerminalService from "@/components/services/TerminalService";
import DateUtil from "@/components/utils/DateUtil";
import TerminalDetailVue from "./TerminalDetail.vue";

export default {
  components: {
    TerminalDetailVue,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      selectedCompany: "",
      companiesList: [],
      terminalId:"",
      isDetail:false
    };
  },
  methods: {
    viewDetails(terminalId) {
      this.terminalId = terminalId;
      this.isDetail = true;
    },
    closeDetail() {
      this.isDetail = false;
      this.populateTerminals();
      this.filterList();
    },
    cancelDetail() {
      this.isDetail = false;
    },
    createTerminal(){
      this.terminalId = '';
      this.isDetail = true;
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
            that.filteredData.sort(that.sortByIdentity);
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
    },
    populateTerminals(){
      let that = this;
      TerminalService.getAllTerminals().then((response) => {
      if (response) {
        that.data = response;
        that.data.sort(this.sortByIdentity);
      }
      });
    },
    sortByIdentity(a, b){
      const identityA = a.identity.toUpperCase();
      const identityB = b.identity.toUpperCase();

      if (identityA < identityB) {
        return -1;
      }
      if (identityA > identityB) {
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
    this.populateTerminals();
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
  padding-left: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}
</style>