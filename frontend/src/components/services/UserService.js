import BaseWS from "./BaseWS";
const srvUser = "/user";


export default {
    getAllUsers() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvUser}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
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