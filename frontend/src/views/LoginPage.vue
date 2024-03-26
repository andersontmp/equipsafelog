<template lang="pug">
.container
  span.text-center Equip Safe Log
  h2 Login
  form(@submit.prevent="fazerLogin")
  .row
    .col
      label.form-label(for="username") Usuário
    .col
      InputText(type="text", v-model="username", required)
  .row
    .col
      label.form-label(for="password") Senha
    .col
      Password(v-model="password" toggleMask feedback=false required)
  div(v-if="invalidPassword")
    RadioButton.margin-top(invalid binary) 
    span.color-red Usuario e/ou senha inválida
  div
    Button.margin-top(type="submit", @click="doLogin") Entrar
</template>
  
  <script>
import AuthService from "@/components/services/AuthService";
import Password from 'primevue/password';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import RadioButton from 'primevue/radiobutton';


export default {
  components:{
    Password,
    InputText,
    Button,
    RadioButton
  },
  data() {
    return {
      username: "",
      password: "",
      invalidPassword: false
    };
  },
  methods: {
    doLogin() {
      let that = this;
      AuthService.login({ login: this.username, password: this.password })
      .then((response) => {
        if (response) {
          that.$store.dispatch("saveData", {
            token: response.token,
            user: that.username,
            pass: that.password,
            role: response.role
          });
          this.$router.push({ name: "Home" });
        }
        })
        .catch((error) => {
          console.error(error);
          that.invalidPassword = true;
        });
    },
  },
};
</script>
  
  <style scoped>
.container {
  text-align: center;
  margin-bottom: 10px;
  color: white;
}

.text-center {
  display: inline-block;
  font-size: x-large;
  font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
  margin-top: 100px;
}

.grid-layout{
  display: flex;
}

.form-label{
  min-width: 200px;
}

.margin-top{
  margin-top: 20px;
}
.color-red{
  margin-left: 10px;
  color: red;
}
</style>
  