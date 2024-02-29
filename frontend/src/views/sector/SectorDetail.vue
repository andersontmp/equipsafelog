<template lang="pug">
form.needs-validation(@submit.prevent="submitForm")
  .row.mb-3 
    .col-md-6
      .form-group
        label.form-label(for="company") Empresa:
        select#company.form-select(
          v-model="sector.company.id",
          required
        )
          option(key="", value="")
          option(
            v-for="item in companiesList",
            :key="item.id",
            :value="item.id"
          ) {{ item.socialName }}
    .col-md-6
      .form-group
        label.form-label(for="identity") Nome:
        input#identity.form-control(
          type="text",
          v-model="sector.name",
          required
        )
        .invalid-feedback Matricula é obrigatória
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="name") Quantidade Mimima:
        input#name.form-control(type="text", v-model="sector.minimalUse", required)
        .invalid-feedback Qauntidade Minima é obrigatória
    .col-md-6
      .form-group
        label.form-label(for="name") Quantidade Maxima:
        input#name.form-control(type="text", v-model="sector.maximalUse", required)
        .invalid-feedback Quantidade Máxima é obrigatória

  button.btn.btn-primary.me-2(type="submit") Salvar
  button.btn.btn-secundary.me-2(type="cancel", @click="cancelDetail") Cancelar
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

export default {
  props: ["id"],
  components: {
    ErrorModal
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
</style>
  