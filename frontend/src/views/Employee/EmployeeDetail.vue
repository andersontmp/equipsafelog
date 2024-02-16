<template lang="pug">
form.needs-validation(@submit.prevent="submitForm")
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="identity") Matricula:
        input#identity.form-control(
          type="text",
          v-model="employee.identity",
          required
        )
        .invalid-feedback Matricula é obrigatória
  .row.mb-3 
    |
    .col-md-6
      .form-group
        label.form-label(for="company") Empresa:
        select#company.form-select(
          v-model="employee.company.id",
          @change="setCompany",
          required
        )
          option(key="", value="")
          option(
            v-for="item in companiesList",
            :key="item.id",
            :value="item.id"
          ) {{ item.socialName }}
  .form-check
    label.form-label(for="active") Ativo
    input#active.form-check-input(type="checkbox", v-model="employee.active")
    
  button.btn.btn-primary(type="submit" class="me-2") Salvar
  button.btn.btn-secundary(type="cancel", @click="cancelDetail" class="me-2") Cancelar
  //button.btn.btn-danger(type="button" @click="removeEmployee") Remover
</template>
  
<script>
import EmployeeService from "@/components/services/EmployeeService";
import CompanyService from "@/components/services/CompanyService";

export default {
  props: ["id"],
  data() {
    return {
      employee: {
        id: "",
        identity: "",
        active: false,
        company: {
          id: "",
        },
      },
      companiesList: [],
    };
  },
  methods: {
    submitForm() {
      let that = this;
      EmployeeService.saveEmployee(this.employee)
        .then((response) => {
          that.employee = response;
          that.$emit("closeDetail", null);
        })
        .catch((error) => {
          console.error("Erro ao gravar os dados dos funcionários:", error);
        });
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
  },
  created() {
    let that = this;
    if (this.id) {
      EmployeeService.getEmployeeById(this.id)
        .then((response) => {
          that.employee = response;
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
  