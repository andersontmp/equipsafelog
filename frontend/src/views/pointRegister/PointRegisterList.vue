<template lang="pug">
div
  .input-group(v-if="!isDetail")
    span.custom-span.me-5 Pesquisa
    span.custom-span Empresa
    select.custom-span(v-model="selectedCompany", @change="setCompany")
      option(key="", value="")
      option(v-for="item in companiesList", :key="item.id", :value="item.id") {{ item.socialName }}
    span.custom-span Funcionario
    select.custom-span(v-model="selectedEmployee")
      option(key="", value="")
      option(v-for="item in employeeList", :key="item.id", :value="item.id") {{ item.identity }}
    span.custom-span Data Inicio
    datepicker#dpStartDate.styleDate.me-2(
      v-model="startDate",
      autoApply,
      :clearable="false",
      :format="formatDate"
    )
    span.custom-span Data Fim
    datepicker#dpEndDate.styleDate.me-2(
      v-model="endDate",
      autoApply,
      :clearable="false",
      :format="formatDate"
    )
    button.custom-span(@click="filterList") Buscar
    button.custom-span(@click="inconsistencyList" :disabled="selectedCompany == ''") Inconsistencias
  div(v-if="typeSearch == 'EMPLOYEE' && !isDetail")
    table.custom-table
      thead
        tr
          th Empregado
          th Terminal
          th Data
      tbody
        tr(
          v-for="item in getListData()",
          :key="item.id",
          @click="viewDetails(item.id)"
        )
          td {{ item.employee.identity }}
          td {{ item.terminal.identity }}
          td {{ formatDateTime(item.date) }}
  div(v-if="typeSearch == 'COMPANY' && !isDetail")
    table.custom-table
      thead
        tr
          th Empregado
          th Data
          th Quantidade
      tbody
        tr(
          v-for="item in getListData()",
          :key="item.id",
          @click="detailRegister(item)"
        )
          td {{ item.employeeIdentity }}
          td {{ formatDate(item.date) }}
          td {{ item.quantity }}
  PointRegisterDetail(
    v-if="isDetail",
    :id="registerId",
    @closeDetail="closeDetail",
    @cancelDetail="cancelDetail"
  )
</template>

<script>
import DateUtil from "@/components/utils/DateUtil";
import PointRegisterService from "@/components/services/PointRegisterService";
import CompanyService from "@/components/services/CompanyService";
import EmployeeService from "@/components/services/EmployeeService";
import PointRegisterDetail from "./PointRegisterDetail.vue";
import Datepicker from "@vuepic/vue-datepicker";

export default {
  components: {
    Datepicker,
    PointRegisterDetail,
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
      typeSearch: "EMPLOYEE",
      registerId: "",
      isDetail: false,
    };
  },
  methods: {
    detailRegister(register) {
      let that = this;
      let criteria = {
        start: register.date,
        end: register.date,
        employeeId: register.employeeId,
      };
      PointRegisterService.getRegistersByEmployee(criteria).then((response) => {
        if (response) {
          that.typeSearch = "EMPLOYEE";
          that.filteredData = response;
          that.filteredData.sort((a, b) => new Date(a.date) - new Date(b.date));
        }
      });
    },
    viewDetails(registerId) {
      this.registerId = registerId;
      this.isDetail = true;
    },
    closeDetail() {
      this.isDetail = false;
      this.populateRegisters();
    },
    cancelDetail() {
      this.isDetail = false;
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
      if (this.selectedCompany != "") {
        EmployeeService.getEmployeeByCompany(this.selectedCompany, false).then(
          (response) => {
            if (response) {
              that.employeeList = response;
            }
          }
        );
      } else {
        that.employeeList = [];
      }
    },
    inconsistencyList(){
      let that = this;
      let criteria = {
        start: this.startDate,
        end: this.endDate,
      };
      if (this.selectedCompany) {
        criteria.companyId = this.selectedCompany;
        PointRegisterService.findInconsistencyByCriteria(criteria).then((response) => {
          if (response) {
            that.typeSearch = "COMPANY";
            that.filteredData = response;
            that.filteredData.sort(
              (a, b) => new Date(a.date) - new Date(b.date)
            );
          }
        });
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
              that.typeSearch = "EMPLOYEE";
              that.filteredData = response;
              that.filteredData.sort(
                (a, b) => new Date(a.date) - new Date(b.date)
              );
            }
          }
        );
      } else if (this.selectedCompany) {
        criteria.companyId = this.selectedCompany;
        PointRegisterService.findByCriteria(criteria).then((response) => {
          if (response) {
            that.typeSearch = "COMPANY";
            that.filteredData = response;
            that.filteredData.sort(
              (a, b) => new Date(a.date) - new Date(b.date)
            );
          }
        });
      } else {
        that.typeSearch = "EMPLOYEE";
        that.filteredData = undefined;
      }
    },
    populateRegisters() {
      this.data = [];
    },
    setStartAndEndDate() {
      this.startDate.setDate(1);
      this.endDate.setDate(1);
      this.endDate.setMonth(this.endDate.getMonth() + 1);
      this.endDate.setDate(this.endDate.getDate() - 1);
    },
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    this.setStartAndEndDate();
    this.populateRegisters();
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

.custom-table th,
.custom-table td {
  border: 1px solid #ddd; /* Define as bordas */
  padding: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}

.styleDate {
  width: 150px;
}
</style>