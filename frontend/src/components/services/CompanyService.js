import BaseWS from "./BaseWS";
const srvCompany = "/company";


export default {
    sortBySocialName(a, b){
        const strA = a.socialName.toUpperCase();
        const strB = b.socialName.toUpperCase();
  
        if (strA < strB) {
          return -1;
        }
        if (strA > strB) {
            return 1;
        }
        return 0;
    },
    getAllCompanies() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvCompany}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    let companiesList = response.data;
                    companiesList.sort(this.sortBySocialName);
                    resolve(companiesList);
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getCompany(companyId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvCompany}/` + companyId, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    saveCompany(company) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvCompany}`, company, { headers: { "Content-Type": "application/json" } }).then((response) => {
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