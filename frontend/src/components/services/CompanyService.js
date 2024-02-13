import BaseWS from "./BaseWS";
const srvCompany = "/company";


export default {
    getAllCompanies() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvCompany}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
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