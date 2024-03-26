<template lang="pug">
.title_container
  span.text-center {{ title }}
div(v-if="!isDetail")
  .input-group
    span.custom-span Pesquisa
    span.custom-span Empresa
    Dropdown(v-model="selectedCompany" :options="companiesList" optionLabel="socialName" placeholder="Selecione uma empresa" @change="setCompany" showClear)
    Button.margin-button(@click="filterList" ) Buscar
  span.custom-span
  DataTable(:value="getListData()" stripedRows size="small" paginator :rows="10" :rowsPerPageOptions="[10, 25, 50, 100]" tableStyle="min-width: 50rem")
    Column(field="identity" header="Matricula")
    Column(header="Ativo")
      template(#body="slotProps")
        i(v-if="slotProps.data.active" class="pi pi-check-circle" style="color: green;")
        i(v-if="!slotProps.data.active" class="pi pi-times-circle" style="color: red;")
    Column(header="Ultima Comunicação")
      template(#body="slotProps")
        span {{ formatDateTime(slotProps.data.lastCommunication) }}
    Column(field="sector.company.socialName" header="Empresa")
    Column(header="Detalhes")
      template(#body="slotProps")
        Button(@click="viewDetails(slotProps.data)" text rounded outlined severity="info" size="small")
          i(class="pi pi-search")
TerminalDetailVue(
  v-if="isDetail",
  :id="terminalId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail")
Toast(position="top-right")
</template>

<script>
import CompanyService from "@/components/services/CompanyService";
import TerminalService from "@/components/services/TerminalService";
import DateUtil from "@/components/utils/DateUtil";
import TerminalDetailVue from "./TerminalDetail.vue";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dropdown from 'primevue/dropdown';
import Toast from "primevue/toast";

export default {
  props: {
    isDetailProp: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    isDetailProp (){
      this.isDetail = this.isDetailProp;
    },
    isDetail(){
      this.title = this.isDetail ? "Terminal" : "Terminais"
    }
  },
  components: {
    TerminalDetailVue,
    DataTable,
    Column,
    Dropdown,
    Toast,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      selectedCompany: undefined,
      companiesList: [],
      terminalId:undefined,
      isDetail:false,
      title:'Terminais',
    };
  },
  methods: {
    viewDetails(terminal) {
      this.terminalId = terminal.id;
      this.isDetail = true;
    },
    closeDetail() {
      this.$toast.add({
        severity: "success",
        summary: "Terminal",
        detail: "Registro salvo com sucesso",
        life: 3000,
      });
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
        TerminalService.getTerminalsByCompany(this.selectedCompany.id).then((response) => {
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
      }
      });
    },
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    if(this.isDetailProp){
      this.isDetail = this.isDetailProp;
    }
  },
};
</script>

<style scoped>
.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
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

.margin-button {
  margin-left: 10px;
}
.title_container{
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