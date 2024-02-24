<template lang="pug">
.error-modal(v-if="errorMessage")
  .modal-content
    span.close(@click="closeError") &times;
    p {{ errorMessage }}
</template>
  
  <script>
export default {
  props: ["errorMessage"],
  methods: {
    closeError() {
      this.$emit("close");
    },
  },
};
</script>
  
<style scoped>
.error-modal {
  display: block;
  position: fixed;
  z-index: 9999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
  