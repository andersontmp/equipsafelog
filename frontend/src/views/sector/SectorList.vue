<template lang="pug">
.title_container
  span.text-center {{ title }}
div(v-if="!isDetail")
  .input-group
    span.custom-span Pesquisa
    span.custom-span Empresa
    Dropdown(v-model="selectedCompany" :options="companiesList" optionLabel="socialName" placeholder="Selecione uma empresa" @change="setCompany" showClear)
    Button.margin-bottom(@click="filterList" ) Buscar
  span.custom-span 
  DataTable(:value="getListData()" stripedRows size="small" paginator :rows="10" :rowsPerPageOptions="[10, 25, 50, 100]" tableStyle="min-width: 50rem")
    Column(field="name" header="Nome")
    Column(field="minimalUse" header="Qtde Minima")
    Column(field="maximalUse" header="Qtde Maxima")
    Column(header="Detalhes")
      template(#body="slotProps")
        Button(@click="viewDetails(slotProps.data)" text rounded outlined severity="info" size="small")
          i(class="pi pi-search")
SectorDetailVue(
  v-if="isDetail",
  :id="sectorId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
Toast(position="top-right")
</template>

<script>
import CompanyService from "@/components/services/CompanyService";
import SectorService from "@/components/services/SectorService";
import SectorDetailVue from './SectorDetail.vue';
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
      this.title = this.isDetail ? "Setor" : "Setores"
    }
  },
  components: {
    SectorDetailVue,
    DataTable,
    Column,
    Dropdown,
    Toast,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      sectorId: undefined,
      selectedCompany: undefined,
      isDetail: false,
      companiesList: [],
      title:'Setores',
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
    viewDetails(sector) {
      this.sectorId = sector.id;
      this.isDetail = true;
    },
    createSector() {
      this.sectorId = "";
      this.isDetail = true;
    },
    closeDetail() {
      this.$toast.add({
        severity: "success",
        summary: "Setor",
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
        SectorService.getSectorByCompany(this.selectedCompany.id, true)
          .then((response) => {
            that.filteredData = response;
            that.filteredData.sort(this.sortByName);
          })
          .catch((error) => {
            console.error("Erro ao obter os dados dos funcionários:", error);
          });
      } else {
        that.filteredData = undefined;
      }
    },
    sortByName(a, b){
      const strA = a.name.toUpperCase();
      const strB = b.name.toUpperCase();

      if (strA < strB) {
        return -1;
      }
      if (strA > strB) {
          return 1;
      }
      return 0;
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

.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}

.margin-bottom {
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