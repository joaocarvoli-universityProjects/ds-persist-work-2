import {Manufacturer} from "./Manufacturer.ts";

export function mapToManufacturer(manufacturerData : any): Manufacturer {
    return {
        id: manufacturerData.id,
        name: manufacturerData.name
    }
}