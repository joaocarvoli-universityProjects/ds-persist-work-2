import {defineStore} from "pinia";
import {api} from "../baseConfig.ts";
import {Manufacturer} from "../model/manufacturer.ts";
import {mapToManufacturer, mapToStock} from "../model/mappers.ts";
import {Stock} from "../model/stock.ts";

export const useManufacturerStore = defineStore('Manufacturer', () => {
    async function getAllManufacturers(): Promise<Manufacturer[]> {
        let manufacturers : Manufacturer[] = []
        try {
            const { data } = await api.get("/manufacturer")
            manufacturers = Object.values(data).map((manufacturer : any) => mapToManufacturer(manufacturer))
            return manufacturers
        } catch (error){
            return manufacturers
        }
    }

    async function getAllByName(field: String, name: String): Promise<Manufacturer[]> {
        let manufacturers : Manufacturer[] = []
        try {
            let query = `/manufacturer/name?field=${field}&name=${name}`
            console.log(query)
            const { data } = await api.get(`/manufacturer/name?field=${field}&name=${name}`)
            manufacturers = Object.values(data).map((manufacturer : any) => mapToStock(manufacturer))
            return manufacturers
        } catch (error){
            return manufacturers
        }
    }

    async function orderByField(field: String, direction: String): Promise<Manufacturer[]> {
        let manufacturers : Manufacturer[] = []
        try {
            const { data } = await api.get(`/manufacturer/order?field=${field}&direction=${direction}`)
            manufacturers = Object.values(data).map((manufacturer : any) => mapToStock(manufacturer))
            return manufacturers
        } catch (error){
            return manufacturers
        }
    }

    async function createManufacturer(manufacturer: Manufacturer): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.post(`manufacturer`, {
                name: manufacturer.name
            })
            if(status == 200){
                requestFeedback.success = true
            } else {
                requestFeedback.success = false
            }

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function editManufacturerById(manufacturer: Manufacturer): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.put(`manufacturer/${manufacturer.id}`, {
                name: manufacturer.name
            })
            if(status == 200){
                requestFeedback.success = true
            } else {
                requestFeedback.success = false
            }

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function removeManufacturerById(manufacturerId: number): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }
        try {
            const {status } = await api.delete(`manufacturer/${manufacturerId}`)
            if(status == 200){
                requestFeedback.success = true
            } else {
                requestFeedback.success = false
            }

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    return {
        createManufacturer, getAllManufacturers, editManufacturerById,
        removeManufacturerById, getAllByName, orderByField
    }
})