<template lang="pug">
.margin-left
  form.needs-validation(@submit.prevent="submitForm")
    .row.mb-3 
      .col-md-6
        .grid-layout
          label.form-label(for="company") Empresa:
          Dropdown#company(v-model="sector.company" :options="companiesList" optionLabel="socialName" placeholder="Selecione uma empresa" showClear :invalid="!sector.company" :disabled="!isCreated")
      .col-md-6
        .grid-layout
          label.form-label(for="identity") Nome:
          InputText#identity.form-control(
            type="text",
            v-model="sector.name",
            required
          )
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="name") Quantidade Mimima:
          InputText#name.form-control(type="text", v-model="sector.minimalUse", required)
      .col-md-6
        .grid-layout
          label.form-label(for="name") Quantidade Maxima:
          InputText#name.form-control(type="text", v-model="sector.maximalUse", required)
    span.custom-span 
    div
      Button.margin-left(type="submit" ) Salvar
      Button.margin-left(type="cancel", @click="cancelDetail") Cancelar
    error-modal(
      :error-message="errorMessage",
    v-if="errorMessage",
      @close="clearError"
    )
</template>
  
<script>
import CompanyService from "@/components/services/CompanyService";
import ErrorModal from "../ErrorModal.vue";
import SectorService from '@/components/services/SectorService';
import Dropdown from 'primevue/dropdown';


export default {
  props: ["id"],
  components: {
    ErrorModal,
    Dropdown
  },
  data() {
    return {
      sector: {
        id: "",
        name: "",
        minimalUse: 0,
        maximalUse: 0,
        company:{
          id:""
        }
      },
      companiesList: [],
      errorMessage: '',
      isCreated: true,
    };
  },
  methods: {
    submitForm() {
      let that = this;
      SectorService.saveSector(this.sector)
        .then((response) => {
          if(response.errorCode){
            that.errorMessage = that.showMessage(response.errorCode);
          }else{
            that.sector = response;
            that.$emit("closeDetail", null);
          }
        })
        .catch((error) => {
          console.error("Erro ao gravar os dados dos funcionários:", error);
        });
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
    clearError() {
      this.errorMessage = '';
    },
  },
  created() {
    let that = this;
    if (this.id) {
      SectorService.getSector(this.id)
        .then((response) => {
          that.sector = response;
          that.isCreated = false;
        })
        .catch((error) => {
          console.error("Erro ao obter os dados dos funcionários:", error);
        });
    }
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
  },
};
</script>
  
  <style>
.invalid-feedback {
  color: red;
}

.grid-layout{
  display: flex;
}
.margin-left{
  padding-left: 10px;
}
.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}
</style>
  