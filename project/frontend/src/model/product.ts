import {Manufacturer} from "./manufacturer.ts";
import {Category} from "./category.ts";

export interface Product {
    id: number,
    price: number,
    name: String,
    manufacturer: Manufacturer,
    manufacturingDate: Date,
    expirationDate: Date,
    category: Category,
    amount: number
}