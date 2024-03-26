import BaseWS from "./BaseWS";
const srvLogin = "/auth";


export default {
    login(data) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvLogin}/login`, data, { headers: { "Content-Type": "application/json" } }).then((response) => {
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