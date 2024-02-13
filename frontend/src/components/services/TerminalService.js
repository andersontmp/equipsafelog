import BaseWS from "./BaseWS";
const srvTerminal = "/terminal";


export default {
    getAllTerminals() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvTerminal}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getTerminalsByCompany(companyId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvTerminal}/company/` + companyId, { headers: { "Content-Type": "application/json" } }).then((response) => {
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