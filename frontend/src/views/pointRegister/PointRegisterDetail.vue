<template lang="pug">
form.needs-validation(@submit.prevent="submitForm")
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="identity") Matricula:
        input#identity.form-control(
          type="text",
          v-model="register.employee.identity",
          disabled
        )
  .row.mb-3 
    |
    |
    .col-md-6
      .form-group
        label.form-label(for="identity") Terminal:
        input#identity.form-control(
          type="text",
          v-model="register.terminal.identity",
          disabled
        )
  .row.mb-3 
    |
    |
    .col-md-6
      .form-group
        label.form-label(for="identity") Data:
        datepicker#dpStartDate.me-2(v-model="register.date" disabled)
  button.btn.btn-primary.me-2(type="submit") Salvar
  button.btn.btn-secundary.me-2(type="cancel", @click="cancelDetail") Cancelar
</template>
  
<script>
import PointRegisterService from "@/components/services/PointRegisterService";
import Datepicker from '@vuepic/vue-datepicker';

export default {
  components: {
    Datepicker
  },
  props: ["id"],
  data() {
    return {
      register: {
        id: "",
        employee: {
          identity: "",
        },
        terminal: {
          identity: "",
        },
      },
    };
  },
  methods: {
    submitForm() {
      let that = this;
      PointRegisterService.updateDatePointRegister(this.register)
        .then((response) => {
          that.register = response;
          that.$emit("closeDetail", null);
        })
        .catch((error) => {
          console.error(
            "Erro ao gravar os dados do registro de utilização:",
            error
          );
        });
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
  },
  created() {
    let that = this;
    if (this.id) {
      PointRegisterService.getRegisterById(this.id)
        .then((response) => {
          let date = new Date(response.date);
          response.date = date;
          that.register = response; 
        })
        .catch((error) => {
          console.error(
            "Erro ao obter os dados do registro de utilização:",
            error
          );
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
  