<template lang="pug">
.margin-left
  form.needs-validation(@submit.prevent="submitForm")
    .row.mb-3 
      |
      .col-md-6
        .grid-layout
          label.form-label(for="active") Ativo
          InputSwitch(v-model="employee.active")
    span.custom-span 
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="identity") Matricula:
          InputText#identity(
            type="text",
            v-model="employee.identity",
            required
          )
    .row.mb-3
      .col-md-6
        .grid-layout
          label.form-label(for="name") Nome:
          InputText#name(type="text", v-model="employee.name", required)
    .row.mb-3 
      |
      |
      .col-md-6
        .grid-layout
          label.form-label(for="company") Empresa:
          Dropdown#company(
            v-model="companyId",
            :options="companiesList",
            optionLabel="socialName",
            placeholder="Selecione uma empresa",
            @change="updateCompanySelected",
            showClear,
            :invalid="!companyId",
            :disabled="!isCreated"
          )
      .col-md-6
        .grid-layout
          label.form-label(for="sector") Setor:
          Dropdown#sector(
            v-model="employee.sector",
            :options="sectorList",
            optionLabel="name",
            placeholder="Selecione um setor",
            showClear,
            :invalid="!employee.sector"
          )
    span.custom-span 
    div
      Button.margin-left(type="submit") Salvar
      Button.margin-left(type="cancel", @click="cancelDetail") Cancelar
    error-modal(
      :error-message="errorMessage",
      v-if="errorMessage",
      @close="clearError"
    )
  .margin-top
    Button.margin-left(
      @click="exportRegisters",
      :disabled="isCreated",
      severity="info"
    ) Exportar Registros
  Toast(position="top-right")
</template>
  
<script>
import EmployeeService from "@/components/services/EmployeeService";
import CompanyService from "@/components/services/CompanyService";
import ErrorModal from "../ErrorModal.vue";
import SectorService from "@/components/services/SectorService";
import PointRegisterService from "@/components/services/PointRegisterService";
import Dropdown from "primevue/dropdown";
import InputSwitch from "primevue/inputswitch";
import Toast from "primevue/toast";

export default {
  props: ["id"],
  components: {
    ErrorModal,
    Dropdown,
    InputSwitch,
    Toast,
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
      companyId: undefined,
      isCreated: true,
    };
  },
  methods: {
    submitForm() {
      let that = this;
      EmployeeService.saveEmployee(this.employee)
        .then((response) => {
          if (response.errorCode) {
            let message = that.showMessage(response.errorCode);
            that.$toast.add({
              severity: "error",
              summary: "Erro ao salvar",
              detail: message,
              life: 3000,
            });
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
          return "Campo Empresa obrigatório";
        case "IDENTIFY_REQUIRED":
          return "Campo Matricula obrigatório";
        case "IDENTIFY_ALREADY_EXISTS":
          return "Matricula já cadastrada para essa empresa";
        case "SECTOR_REQUIRED":
          return "Campo Setor é obrigatório";
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
        SectorService.getSectorByCompany(this.companyId.id).then((response) => {
          if (response) {
            that.sectorList = response;
          }
        });
      } else {
        that.employee.sector = undefined;
      }
    },
    exportRegisters() {
      let that = this;
      PointRegisterService.exportRegisterByEmployee(that.employee.id).then(
        (response) => {
          if (response) {
            const blob = new Blob([response.join("")], { type: "text/plain" });
            const url = window.URL.createObjectURL(blob);
            const linkDeDownload = document.createElement("a");
            linkDeDownload.href = url;
            linkDeDownload.download =
              "PointRegisters_" + that.employee.identity + ".csv";
            document.body.appendChild(linkDeDownload);
            linkDeDownload.click();
            document.body.removeChild(linkDeDownload);
            window.URL.revokeObjectURL(url);
            that.$toast.add({
              severity: "success",
              summary: "Colaborador",
              detail: "Registro histórico exportado com sucesso",
              life: 3000,
            });
          }
        }
      );
    },
  },
  created() {
    let that = this;
    if (this.id) {
      this.isCreated = false;
      EmployeeService.getEmployeeById(this.id)
        .then((response) => {
          that.employee = response;
          that.companyId = that.employee.sector.company;
          SectorService.getSectorByCompany(that.companyId.id).then(
            (response) => {
              if (response) {
                that.sectorList = response;
              }
            }
          );
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

.margin-top {
  margin-top: 10px;
}

.btn_color {
  border: none;
  border-radius: 4px; /* Cantos arredondados */
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: rgb(0, 74, 172); /* Sua cor personalizada */
  color: white;
}
.btn_color:hover {
  background-color: rgb(0, 74, 172);
}

.grid-layout {
  display: flex;
}

.form-label {
  color: white;
  min-width: 200px;
}

.margin-left {
  padding-left: 10px;
}
.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}
</style>
  