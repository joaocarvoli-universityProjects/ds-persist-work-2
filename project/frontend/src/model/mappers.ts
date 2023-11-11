import {Category} from "./category.ts";
import {Manufacturer} from "./manufacturer.ts";

export function mapToManufacturer(manufacturerData : any): Manufacturer {
    return {
        id: manufacturerData.id,
        name: manufacturerData.name
    }
}

export function mapToCategory(categoryData: any): Category {
    return {
        id: categoryData.id,
        name: categoryData.name
    }
}