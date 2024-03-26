<template lang="pug">
.title_container
  span.text-center {{ title }}
div
  .input-group(v-if="!isDetail")
    span.custom-span Pesquisa
    span.custom-span Empresa
    Dropdown(v-model="selectedCompany" :options="companiesList" optionLabel="socialName" placeholder="Selecione uma empresa" @change="setCompany" showClear)
    span.custom-span Colaborador
    AutoComplete(v-model="selectedEmployee" dropdown :suggestions="employeeListFiltered" @complete="searchSuggest")
    //Dropdown(v-model="selectedEmployee" :options="employeeList" optionLabel="identity" placeholder="Selecione um colaborador" showClear)
    span.custom-span Mes
    Calendar(v-model="startDate" view="month" dateFormat="mm/yy")
    Button.margin-button(@click="filterList" ) Buscar
    Button.margin-button(@click="inconsistencyList" :disabled="!selectedCompany" severity="warning") Inconsistencias
  span.custom-span
  div(v-if="typeSearch == 'EMPLOYEE' && !isDetail")
    DataTable(:value="getListData()" stripedRows size="small" paginator :rows="10" :rowsPerPageOptions="[10, 25, 50, 100]" tableStyle="min-width: 50rem")
      Column(header="Colaborador")
        template(#body="slotProps")
          span {{ slotProps.data.employee.identity }} - {{ slotProps.data.employee.name }}
      Column(field="terminal.identity" header="Terminal")
      Column(header="Data")
        template(#body="slotProps")
          span {{ formatDateTime(slotProps.data.date) }}
      Column(header="Detalhes")
        template(#body="slotProps")
          Button(@click="viewDetails(slotProps.data)" text rounded outlined severity="info" size="small")
            i(class="pi pi-search")
  div(v-if="typeSearch == 'COMPANY' && !isDetail")
    DataTable(:value="getListData()" stripedRows size="small" paginator :rows="10" :rowsPerPageOptions="[10, 25, 50, 100]" tableStyle="min-width: 50rem")
      Column(field="employeeIdentity" header="Colaborador")
        template(#body="slotProps")
          span {{ slotProps.data.employeeIdentity }} - {{ slotProps.data.employeeName }}
      Column(header="Data")
        template(#body="slotProps")
          span {{ formatDate(slotProps.data.date) }}
      Column(field="quantity" header="Quantidade")
      Column(header="Detalhes")
        template(#body="slotProps")
          Button(@click="detailRegister(slotProps.data)" text rounded outlined severity="info" size="small")
            i(class="pi pi-search")
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
import Calendar from 'primevue/calendar';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import Dropdown from 'primevue/dropdown';
import AutoComplete from 'primevue/autocomplete';


export default {
  components: {
    Calendar,
    PointRegisterDetail,
    DataTable,
    Column,
    Button,
    Dropdown,
    AutoComplete
  },
  data() {
    return {
      data: [],
      filteredData: undefined,
      selectedCompany: undefined,
      companiesList: [],
      selectedEmployee: undefined,
      employeeList: [],
      employeeListFiltered: [],
      startDate: null,
      typeSearch: "EMPLOYEE",
      registerId: "",
      isDetail: false,
      title:'Registros de Utilização',
    };
  },
  methods: {
    searchSuggest(event){
      this.employeeListFiltered = this.employeeList.filter((f) => f.identity.toUpperCase().includes(event.query.toUpperCase()) || f.name.toUpperCase().includes(event.query.toUpperCase())) .map((item) => item.identity + " - " + item.name)
    },
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
      this.registerId = registerId.id;
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
      if (this.selectedCompany) {
        EmployeeService.getEmployeeByCompany(this.selectedCompany.id, false).then(
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
      criteria.start.setTime(this.startDate.getTime());
      criteria.end.setTime(this.startDate.getTime());
      
      criteria.start.setDate(1);

      criteria.end.setMonth(criteria.end.getMonth() +1);
      criteria.end.setDate(1);
      criteria.end.setDate(criteria.end.getDate() - 1);
      return criteria;
    },
    inconsistencyList(){
      let that = this;
      let criteria = this.mountCriteria();
      if (this.selectedCompany || this.selectedEmployee) {
        criteria.companyId = this.selectedCompany?.id;
        if(this.selectedEmployee){
          criteria.employeeId = this.employeeList.filter((f) => f.identity === this.selectedEmployee.split(" - ")[0])[0].id;
        }
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
    sortBySocialName(a, b) {
        if(a.date != b.date){
          return new Date(a.date) - new Date(b.date);
        }        
        const strA = a.employeeIdentity.toUpperCase();
        const strB = b.employeeIdentity.toUpperCase();

        if (strA < strB) {
            return -1;
        }
        if (strA > strB) {
            return 1;
        }
        return 0;
    },
    filterList() {
      let that = this;
      let criteria = this.mountCriteria();

      if (this.selectedEmployee) {
        criteria.employeeId = that.employeeList.filter((f) => f.identity === that.selectedEmployee.split(" - ")[0])[0].id;
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
        criteria.companyId = this.selectedCompany?.id;
        PointRegisterService.findByCriteria(criteria).then((response) => {
          if (response) {
            that.typeSearch = "COMPANY";
            that.filteredData = response;
            that.filteredData.sort(this.sortBySocialName);
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
    this.startDate =new Date();
  },
};
</script>

<style scoped>
.custom-span {
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  color: white;
}

.margin-button {
  margin-left: 10px;
}
.title_container{
  text-align: center;
}
.text-center {
  display: inline-block;
  font-size: large;
  padding-bottom: 10px;
  color: white;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

</style>