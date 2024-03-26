<template lang="pug">
.margin-left
  form.needs-validation(@submit.prevent="submitForm")
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="socialName") Razao Social:
          InputText#socialName(
            type="text",
            v-model="company.socialName",
            required,
            :disabled="!isAdmin()"
          )
      .col-md-6
        .grid-layout
          label.form-label(for="cnpj") CNPJ:
          InputMask#cnpj(
            type="text",
            v-model="company.cnpj",
            mask="99.999.999/9999-99",
            placeholder="00.000.000/0000-00",
            required,
            :disabled="!isAdmin()"
          )
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="responsable") Responsável:
          InputText#responsable(
            type="text",
            v-model="company.responsable",
            required
          )
      .col-md-6
        .grid-layout
          label.form-label(for="phone") Telefone:
          InputMask#phone(
            type="text",
            v-model="company.phone",
            mask="99-99999999?9",
            placeholder="00-000000000"
            required
          )
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="weekendWork") Trabalha aos finais de semana
          Checkbox(v-model="company.weekendWork" :binary="true" )
    span.custom-span 
    div
      Button.margin-left(type="submit" ) Salvar
      Button.margin-left(type="cancel", @click="cancelDetail") Cancelar
</template>
  
<script>
import CompanyService from "@/components/services/CompanyService";
import InputMask from 'primevue/inputmask';
import Checkbox from 'primevue/checkbox';

export default {
  components:{
    Checkbox,
    InputMask
  },
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
    isAdmin(){
      return this.$store.getters.getData?.role == "ADMIN";
    }
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

.grid-layout{
  display: flex;
}

.form-label{
  color: white;
  min-width: 200px;
}

.margin-left{
  margin-left: 10px;
}

.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}

</style>
  