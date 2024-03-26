<template lang="pug">
.margin-left
  form.needs-validation(@submit.prevent="submitForm")
    .row.mb-3 
      .col-md-6
        .grid-layout
          label.form-label(for="active") Ativo
          InputSwitch(v-model="terminal.active" :disabled="!isAdmin()")
    span.custom-span 
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="identity") Matricula:
          InputText#identity.form-control(type="text", v-model="terminal.identity" :disabled="!isAdmin()")
    .row.mb-3 
      |
      .col-md-6
        .grid-layout
          label.form-label(for="company") Empresa:
          Dropdown#company(v-model="companyId" :options="companiesList" optionLabel="socialName" placeholder="Selecione uma empresa" @change="updateCompanySelected" showClear :invalid="!companyId" :disabled="!isAdmin()")
      .col-md-6
        .grid-layout
          label.form-label(for="sector") Setor:
          Dropdown#sector(v-model="terminal.sector" :options="sectorList" optionLabel="name" placeholder="Selecione um setor" showClear :invalid="!terminal.sector" )
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="identity") Ultima Comunicação:
          Calendar(id="calendar-24h" v-model="terminal.lastCommunication" showTime hourFormat="24" disabled)
    span.custom-span 
    div
      Button.margin-left(type="submit" ) Salvar
      Button.margin-left(type="cancel", @click="cancelDetail") Cancelar
</template>
  
<script>
import Calendar from 'primevue/calendar';
import TerminalService from "@/components/services/TerminalService";
import CompanyService from "@/components/services/CompanyService";
import SectorService from "@/components/services/SectorService";
import Dropdown from 'primevue/dropdown';
import InputSwitch from 'primevue/inputswitch';

export default {
  components: {
    Calendar,
    InputSwitch,
    Dropdown
  },
  props: ["id"],
  data() {
    return {
      terminal: {
        id: "",
        identity: "",
        sector: {
          id: "",
        },
        lastCommunication: null,
        createDate: new Date(),
      },
      companyId: undefined,
      companiesList: [],
      sectorList: [],
    };
  },
  methods: {
    submitForm() {
      let that = this;
      TerminalService.saveTerminal(this.terminal)
        .then((response) => {
          that.terminal = response;
          that.$emit("closeDetail", null);
        })
        .catch((error) => {
          console.error("Erro ao gravar os dados do terminal:", error);
        });
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
    updateCompanySelected() {
      let that = this;
      if (this.companyId) {
        SectorService.getSectorByCompany(this.companyId.id).then((response) => {
          if (response) {
            that.sectorList = response;
          }
        });
      }
    },
    isAdmin(){
      return this.$store.getters.getData?.role == "ADMIN";
    }
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
  },
  created() {
    let that = this;
    if (this.id) {
      TerminalService.getTerminalById(this.id)
        .then((response) => {
          if(response.lastCommunication){
            let date = new Date(response.lastCommunication);
            response.lastCommunication = date;
          }
          that.terminal = response;
          that.companyId = that.terminal.sector.company;
          SectorService.getSectorByCompany(that.companyId.id).then((response) => {
            if (response) {
              that.sectorList = response;
            }
          });
        })
        .catch((error) => {
          console.error("Erro ao obter os dados do terminal:", error);
        });
    }
  },
};
</script>
  
  <style>
.invalid-feedback {
  color: red;
}
.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}
</style>
  