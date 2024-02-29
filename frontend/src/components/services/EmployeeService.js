import BaseWS from "./BaseWS";
const srvEmployee = "/employee";


export default {
    sortByIdentity(a, b) {
        const strA = a.identity.toUpperCase();
        const strB = b.identity.toUpperCase();

        if (strA < strB) {
            return -1;
        }
        if (strA > strB) {
            return 1;
        }
        return 0;
    },
    getAllEmployees() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvEmployee}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    let employeeList = response.data;
                    employeeList.sort(this.sortByIdentity);
                    resolve(employeeList)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getEmployeeById(employeeId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvEmployee}/` + employeeId, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getEmployeeByCompany(companyId, loadAllData) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvEmployee}/company/` + companyId + '/' + loadAllData, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    let employeeList = response.data;
                    employeeList.sort(this.sortByIdentity);
                    resolve(employeeList)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    saveEmployee(employee) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvEmployee}`, employee, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
}