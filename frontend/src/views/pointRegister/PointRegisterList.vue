<template lang="pug">
div
  div
    span.custom-span Empresa
    select.custom-span(v-model="selectedCompany", @change="setCompany")
      option(key="", value="")
      option(v-for="item in companiesList", :key="item.id", :value="item.id") {{ item.socialName }}
    span.custom-span Funcionario
    select.custom-span(v-model="selectedEmployee")
      option(key="", value="")
      option(v-for="item in employeeList", :key="item.id", :value="item.id") {{ item.identity }}
  .container(style="{ display: 'flex' }")
    span.custom-span Data Inicio
    datepicker(v-model="startDate")
    span.custom-span Data Fim
    datepicker(v-model="endDate")
    button.custom-span(@click="filterList") Buscar
  div(v-if="typeSearch=='EMPLOYEE'")
    table.custom-table
      thead
        tr
          th Empregado
          th Terminal
          th Data
      tbody
        tr(v-for="item in getListData()", :key="item.id", @click="viewDetails(item.id)")
          td {{ item.employee.identity }}
          td {{ item.terminal.identity }}
          td {{ formatDateTime(item.date) }}
  div(v-if="typeSearch=='COMPANY'")
    table.custom-table
      thead
        tr
          th Empregado
          th Data
          th Quantidade
      tbody
        tr(v-for="item in getListData()", :key="item.id", @click="viewDetails(item.id)")
          td {{ item.employeeIdentity }}
          td {{ formatDate(item.date) }}
          td {{ item.quantity }}
</template>

<script>
import DateUtil from "@/components/utils/DateUtil";
import PointRegisterService from "@/components/services/PointRegisterService";
import CompanyService from "@/components/services/CompanyService";
import Datepicker from "vue3-datepicker";
import EmployeeService from "@/components/services/EmployeeService";

export default {
  components: {
    Datepicker,
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      selectedCompany: "",
      companiesList: [],
      selectedEmployee: "",
      employeeList: [],
      startDate: new Date(),
      endDate: new Date(),
      typeSearch:'EMPLOYEE'
    };
  },
  methods: {
    viewDetails(registerId) {
      //this.$router.push({ name: "EmployeeDetail", params: { id: employeeId } });
      console.log(registerId);
    },
    getListData() {
      if (this.filteredData) {
        return this.filteredData;
      } else {
        return this.data;
      }
    },
    formatDate(input) {
      return DateUtil.formatDate(input);
    },
    formatDateTime(input) {
      return DateUtil.formatDateTime(input);
    },
    setCompany() {
      let that = this;
      this.selectedEmployee = undefined;
      if(this.selectedCompany != ''){
        EmployeeService.getEmployeeByCompany(this.selectedCompany).then(
          (response) => {
            if (response) {
              that.employeeList = response;
            }
          }
        );
      }else{
        that.employeeList = []; 
      }
    },
    filterList() {
      let that = this;
      let criteria = {
        start: this.startDate,
        end: this.endDate,
      };
      if (this.selectedEmployee) {
        criteria.employeeId = this.selectedEmployee;
        PointRegisterService.getRegistersByEmployee(criteria).then(
          (response) => {
            if (response) {
              that.typeSearch='EMPLOYEE'
              that.filteredData = response;
              that.filteredData.sort((a,b) => new Date(a.date) - new Date(b.date));
            }
          }
        );
      } else if (this.selectedCompany) {
        criteria.companyId = this.selectedCompany;
        PointRegisterService.findByCriteria(criteria).then((response) => {
          if (response) {
            that.typeSearch = 'COMPANY'
            that.filteredData = response;
            that.filteredData.sort((a,b) => new Date(a.date) - new Date(b.date));
          }
        });
      } else {
        that.typeSearch = 'EMPLOYEE';
        that.filteredData = undefined;
      }
    },
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    PointRegisterService.getAllRegisters().then((response) => {
      if (response) {
        that.data = response;
        that.data.sort((a,b) => new Date(a.date) - new Date(b.date));
      }
    });
  },
};
</script>

<style scoped>
.custom-span {
  margin-right: 20px;
  margin-bottom: 10px;
}

.container {
  display: flex; /* ou qualquer outro valor como 'space-around', 'center', 'space-evenly' */
}

.table-container {
  overflow-x: auto; /* Adiciona uma barra de rolagem horizontal se a tabela for muito larga */
}

.custom-table {
  width: 100%;
  border-collapse: collapse;
}

.custom-table th, .custom-table td {
  border: 1px solid #ddd; /* Define as bordas */
  padding: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}
</style>