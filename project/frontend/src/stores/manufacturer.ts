import {defineStore} from "pinia";
import {api} from "../baseConfig.ts";
import {Manufacturer} from "../model/Manufacturer.ts";
import {mapToManufacturer} from "../model/Mappers.ts";

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

    return { getAllManufacturers }
})