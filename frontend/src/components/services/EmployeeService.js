import BaseWS from "./BaseWS";
const srvEmployee = "/employee";


export default {
    getAllEmployees() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvEmployee}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getEmployeeByCompany(companyId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvEmployee}/company/` + companyId, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    }
}