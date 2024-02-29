import BaseWS from "./BaseWS";
const srvSector = "/sector";


export default {
    getAllSectors() {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvSector}`, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getSector(sectorId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvSector}/` + sectorId, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    saveSector(sector) {
        return new Promise((resolve, reject) => {
            BaseWS.post(`${srvSector}`, sector, { headers: { "Content-Type": "application/json" } }).then((response) => {
                if (response) {
                    resolve(response.data)
                }
            }).catch((error) => {
                console.log(error);
                reject(error);
            });
        })
    },
    getSectorByCompany(companyId) {
        return new Promise((resolve, reject) => {
            BaseWS.get(`${srvSector}/company/` + companyId, { headers: { "Content-Type": "application/json" } }).then((response) => {
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