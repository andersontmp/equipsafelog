<template lang="pug">
form.needs-validation(@submit.prevent="submitForm")
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="identity") Matricula:
        input#identity.form-control(type="text", v-model="terminal.identity")
  .row.mb-3 
    .col-md-6
      .form-group
        label.form-label(for="socialName") Empresa:
        input#socialName.form-control(
          type="text",
          v-model="terminal.company.socialName",
          disabled
        )
  .form-check
    label.form-label(for="active") Ativo
    input#active.form-check-input(type="checkbox", v-model="terminal.active")
  .row.mb-3
    .col-md-6
      .form-group
        label.form-label(for="identity") Ultima Comunicação:
        datepicker#dpStartDate.me-2(v-model="terminal.lastCommunication", disabled)
  button.btn.btn-primary.me-2(type="submit") Salvar
  button.btn.btn-secundary.me-2(type="cancel", @click="cancelDetail") Cancelar
</template>
  
<script>
import Datepicker from "@vuepic/vue-datepicker";
import TerminalService from "@/components/services/TerminalService";

export default {
  components: {
    Datepicker,
  },
  props: ["id"],
  data() {
    return {
      terminal: {
        id: "",
        identity: "",
        company: {
          identity: "",
        },
        lastCommunication: null,
        createDate: new Date(),
      },
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
          console.error(
            "Erro ao gravar os dados do terminal:",
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
      TerminalService.getTerminalById(this.id)
        .then((response) => {
          let date = new Date(response.lastCommunication);
          response.lastCommunication = date;
          that.terminal = response;
        })
        .catch((error) => {
          console.error(
            "Erro ao obter os dados do terminal:",
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
  