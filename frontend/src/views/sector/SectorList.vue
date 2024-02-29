<template lang="pug">
div(v-if="!isDetail")
  .d-flex.justify-content-start
    button.btn.btn-success.me-2(
      type="button",
      @click="createSector",
      title="Adicionar Setor"
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
        th Nome
        th Qtde Minima
        th Qtde Maxima
        th Detalhes
    tbody
      tr(
        v-for="item in getListData()",
        :key="item.id"
      )
        td {{ item.name }}
        td {{ item.minimalUse }}
        td {{ item.maximalUse }}
        td
          button.btn.btn-warning.btn-sm(type="button" @click="viewDetails(item.id)" title="Editar") >
SectorDetailVue(
  v-if="isDetail",
  :id="sectorId",
  @closeDetail="closeDetail",
  @cancelDetail="cancelDetail"
)
</template>

<script>
import CompanyService from "@/components/services/CompanyService";
import SectorService from "@/components/services/SectorService";
import SectorDetailVue from './SectorDetail.vue';

export default {
  components: {
    SectorDetailVue
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      sectorId: "",
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
    viewDetails(sectorId) {
      this.sectorId = sectorId;
      this.isDetail = true;
    },
    createSector() {
      this.sectorId = "";
      this.isDetail = true;
    },
    closeDetail() {
      this.isDetail = false;
      this.filterList();
    },
    cancelDetail() {
      this.isDetail = false;
    },
    filterList() {
      let that = this;
      if (this.selectedCompany) {
        SectorService.getSectorByCompany(this.selectedCompany, true)
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