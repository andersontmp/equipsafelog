<template lang="pug">
form.needs-validation(@submit.prevent="submitForm" style="padding-left=40px;")
  .row.mb-3
    .col-md-6
      .grid-layout
        label.form-label(for="identity") Matricula:
        InputText.right_align(
          type="text",
          v-model="register.employee.identity",
          disabled
        )
  .row.mb-3 
    .col-md-6
      .grid-layout
        label.form-label(for="identity") Terminal:
        InputText.right_align(
          type="text",
          v-model="register.terminal.identity",
          disabled
        )
  .row.mb-3 
    .col-md-6
      .grid-layout
        label.form-label(for="identity") Data:
        Calendar.right_align(id="calendar-24h" v-model="register.date" showTime hourFormat="24" disabled)
  Button.margin-button(@click="cancelDetail" ) Sair
</template>
  
<script>
import PointRegisterService from "@/components/services/PointRegisterService";
import Calendar from 'primevue/calendar';
import InputText from 'primevue/inputtext';


export default {
  components: {
    Calendar,
    InputText
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
.grid-layout{
  display: flex;
}
.invalid-feedback {
  color: red;
}

.form-label{
  color: white;
  min-width: 200px;
}
.right_align{
  text-align:right;
  
}
</style>
  