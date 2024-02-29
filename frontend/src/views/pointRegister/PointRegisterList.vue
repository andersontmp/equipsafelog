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
    span.custom-span Mes
    datepicker#dpStartDate.styleDate.me-2(
      v-model="startDate",
      autoApply,
      :clearable="false",
      month-picker
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
          th Detalhes
      tbody
        tr(
          v-for="item in getListData()",
          :key="item.id",
          
        )
          td {{ item.employee.identity }} - {{ item.employee.name }}
          td {{ item.terminal.identity }}
          td {{ formatDateTime(item.date) }}
          td
            button.btn.btn-warning.btn-sm(type="button" @click="viewDetails(item.id)" title="Editar") >
  div(v-if="typeSearch == 'COMPANY' && !isDetail")
    table.custom-table
      thead
        tr
          th Empregado
          th Data
          th Quantidade
          th Listar
      tbody
        tr(
          v-for="item in getListData()",
          :key="item.id",
          
        )
          td {{ item.employeeIdentity }} - {{ item.employeeName }}
          td {{ formatDate(item.date) }}
          td {{ item.quantity }}
          td
            button.btn.btn-success.btn-sm(type="button" @click="detailRegister(item)" title="Listar" :disabled="item.quantity <= 0") >
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
      startDate: null,
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
      return DateUtil.formatDateWithWeek(input);
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
    mountCriteria(){
      let criteria = {
        start: new Date(),
        end: new Date(),
      };
      criteria.start.setFullYear(this.startDate.year);
      criteria.start.setMonth(this.startDate.month);
      criteria.start.setDate(1);

      criteria.end.setFullYear(this.startDate.year);
      criteria.end.setMonth(this.startDate.month +1);
      criteria.end.setDate(1);
      criteria.end.setDate(criteria.end.getDate() - 1);
      return criteria;
    },
    inconsistencyList(){
      let that = this;
      let criteria = this.mountCriteria();
      if (this.selectedCompany || this.selectedEmployee) {
        criteria.companyId = this.selectedCompany;
        criteria.employeeId = this.selectedEmployee;
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
      let criteria = this.mountCriteria();

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
  },
  mounted() {
    let that = this;
    CompanyService.getAllCompanies().then((response) => {
      if (response) {
        that.companiesList = response;
      }
    });
    this.populateRegisters();
    this.startDate ={
      month: new Date().getMonth(),
      year: new Date().getFullYear()
    }
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
  padding-left: 8px; /* Adiciona espaçamento interno */
  text-align: left; /* Alinha o texto à esquerda */
}

.custom-table th {
  background-color: #f2f2f2; /* Adiciona uma cor de fundo para os cabeçalhos */
}

.styleDate {
  width: 150px;
}
</style>