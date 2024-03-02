import BaseWS from "./BaseWS";
const srvRegister = "/pointregister";


export default {
    getAllRegisters() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvRegister}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getRegisterById(id) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvRegister}/` + id, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    findByCriteria(criteria) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvRegister}/criteria`, criteria, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getRegistersByEmployee(criteria) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvRegister}/employee`, criteria, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    updateDatePointRegister(register) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvRegister}/updateDate`, register, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    findInconsistencyByCriteria(criteria) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvRegister}/inconsistency`, criteria, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    exportRegisterByEmployee(employeeId){
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvRegister}/export/`+employeeId,{ headers: { "Content-Type": "application/json" } }).then((response) => {
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