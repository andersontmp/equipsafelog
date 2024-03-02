<template lang="pug">
div
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
      .col-md-6
        .form-group
          label.form-label(for="name") Nome:
          input#name.form-control(type="text", v-model="employee.name", required)
          .invalid-feedback Nome é obrigatório
    .row.mb-3 
      |
      .col-md-6
        .form-group
          label.form-label(for="company") Empresa:
          select#company.form-select(
            v-model="companyId",
            @change="updateCompanySelected",
            required
            :disabled="!isCreated"
          )
            option(key="", value="")
            option(
              v-for="item in companiesList",
              :key="item.id",
              :value="item.id"
            ) {{ item.socialName }}
      .col-md-6
        .form-group
          label.form-label(for="sector") Setor:
          select#sector.form-select(v-model="employee.sector.id", required)
            option(key="", value="")
            option(v-for="item in sectorList", :key="item.id", :value="item.id") {{ item.name }}
    .form-check
      label.form-label(for="active") Ativo
      input#active.form-check-input(type="checkbox", v-model="employee.active")

    div
      button.btn.btn-primary.me-2(type="submit") Salvar
      button.btn.btn-secundary.me-2(type="cancel", @click="cancelDetail") Cancelar
    error-modal(
      :error-message="errorMessage",
      v-if="errorMessage",
      @close="clearError"
    )
  .margin-top
    button.btn_color(@click="exportRegisters" :disabled="isCreated") Exportar Registros 
</template>
  
<script>
import EmployeeService from "@/components/services/EmployeeService";
import CompanyService from "@/components/services/CompanyService";
import ErrorModal from "../ErrorModal.vue";
import SectorService from "@/components/services/SectorService";
import PointRegisterService from "@/components/services/PointRegisterService";

export default {
  props: ["id"],
  components: {
    ErrorModal,
  },
  data() {
    return {
      employee: {
        id: "",
        identity: "",
        active: false,
        sector: {
          id: "",
          company: {
            id: "",
          },
        },
      },
      companiesList: [],
      sectorList: [],
      errorMessage: "",
      companyId: "",
      isCreated:true
    };
  },
  methods: {
    submitForm() {
      let that = this;
      EmployeeService.saveEmployee(this.employee)
        .then((response) => {
          if (response.errorCode) {
            that.errorMessage = that.showMessage(response.errorCode);
          } else {
            that.employee = response;
            that.$emit("closeDetail", null);
          }
        })
        .catch((error) => {
          console.error("Erro ao gravar os dados dos funcionários:", error);
        });
    },
    showMessage(e) {
      switch (e) {
        case "COMPANY_REQUIRED":
          return "Campo Empresa obrigatorio";
        case "IDENTIFY_REQUIRED":
          return "Campo Matricula obrigatorio";
        case "IDENTIFY_ALREADY_EXISTS":
          return "Matricula já cadastrada para essa empresa";
      }
    },
    cancelDetail() {
      this.$emit("cancelDetail", null);
    },
    clearError() {
      this.errorMessage = "";
    },
    updateCompanySelected() {
      let that = this;
      if (this.companyId) {
        SectorService.getSectorByCompany(this.companyId).then((response) => {
          if (response) {
            that.sectorList = response;
           
          }
        });
      }
    },
    exportRegisters(){
      let that = this;
      PointRegisterService.exportRegisterByEmployee(that.employee.id).then(response => {
          if (response) {
            const blob = new Blob([response.join("")], { type: 'text/plain' });
            const url = window.URL.createObjectURL(blob);
            const linkDeDownload = document.createElement('a');
            linkDeDownload.href = url;
            linkDeDownload.download = "PointRegisters_"+that.employee.identity+ ".csv";
            document.body.appendChild(linkDeDownload);
            linkDeDownload.click();
            document.body.removeChild(linkDeDownload);
            window.URL.revokeObjectURL(url);
          }
        })
    }
  },
  created() {
    let that = this;
    if (this.id) {
      this.isCreated = false
      EmployeeService.getEmployeeById(this.id)
        .then((response) => {
          that.employee = response;
          that.companyId = that.employee.sector.company.id;
            SectorService.getSectorByCompany(that.companyId).then((response) => {
              if (response) {
                that.sectorList = response;
              }
            });
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

.margin-top{
  margin-top: 10px;
}

.btn_color{
  border: none;
  border-radius: 4px; /* Cantos arredondados */
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: rgb(0, 74, 172); /* Sua cor personalizada */
  color: white;
}
.btn_color:hover{
  background-color: rgb(0, 74, 172); 
}
</style>
  