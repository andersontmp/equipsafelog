<template lang="pug">
form.needs-validation(@submit.prevent="submitForm")
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="socialName") Razao Social:
        input#socialName.form-control(
          type="text",
          v-model="company.socialName",
          required
        )
        .invalid-feedback Razao Social é obrigatória
    .col-md-6
      .form-group
        label.form-label(for="cnpj") CNPJ:
        input#cnpj.form-control(
          type="text",
          v-model="company.cnpj",
          placeholder="00.000.000/0000-00",
          required
        )
        .invalid-feedback CNPJ é obrigatório
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="responsable") Responsável:
        input#responsable.form-control(
          type="text",
          v-model="company.responsable",
          required
        )
        .invalid-feedback Responsável é obrigatório
    .col-md-6
      .form-group
        label.form-label(for="phone") Telefone:
        input#phone.form-control(
          type="text",
          v-model="company.phone",
          required
        )
        .invalid-feedback Telefone é obrigatório
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="minimalUse") Uso mínimo:
        input#minimalUse.form-control(
          type="text",
          v-model="company.minimalUse",
          required
        )
        .invalid-feedback Uso mínimo é obrigatorio
    .col-md-6
      .form-group
        label.form-label(for="maximalUse") Uso maximo:
        input#maximalUse.form-control(
          type="text",
          v-model="company.maximalUse",
          required
        )
        .invalid-feedback Uso máximo é obrigatório
        
  .form-check
    label.form-label(for="weekendWork") Trabalha aos finais de semana
    input#weekendWork.form-check-input(type="checkbox", v-model="company.weekendWork")
  button.btn.btn-primary(type="submit") Salvar
  button.btn.btn-secundary(type="cancel", @click="cancelDetail") Cancelar
</template>
  
<script>
import CompanyService from "@/components/services/CompanyService";

export default {
  props: ["id"],
  data() {
    return {
      company: {
        socialName: "",
        cnpj: "",
        responsable: "",
        phone: "",
      },
    };
  },
  methods: {
    submitForm() {
      let that = this;
      CompanyService.saveCompany(this.company)
        .then((response) => {
          that.company = response;
          that.$emit("closeDetail", null);
        })
        .catch((error) => {
          console.error("Erro ao gravar os dados da empresa:", error);
        });
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
  },
  created() {
    let that = this;
    if (this.id) {
      CompanyService.getCompany(this.id)
        .then((response) => {
          that.company = response;
        })
        .catch((error) => {
          console.error("Erro ao obter os dados da empresa:", error);
        });
    }
  },
};
</script>
  
  <style>
.invalid-feedback {
  color: red;
}
</style>
  