<template lang="pug">
div
  h2 Login
  form(@submit.prevent="fazerLogin")
  div
    label(for="username") Usuário:
    input#username(type="text", v-model="username", required)
  div
    label(for="password") Senha:
    input#password(type="password", v-model="password", required)
  div
    button(type="submit", @click="doLogin") Entrar
</template>
  
  <script>
import AuthService from "@/components/services/AuthService";
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    doLogin() {
      AuthService.login({ username: this.username, password: this.password })
        .then((response) => {
          if (response) {
            this.$router.push({ name: "Home" });
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>
  
  <style scoped>
/* Estilos específicos da página de login */
</style>
  