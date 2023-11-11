import {Category} from "./category.ts";
import {Manufacturer} from "./manufacturer.ts";
import {Product} from "./product.ts";
import {ProductDto} from "../views/dto/productDto.ts";

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

export function mapToProduct(productData: any): Product {
    return {
        id: productData.id,
        name: productData.name,
        manufacturer: productData.manufacturer,
        manufacturingDate: productData.manufacturingDate,
        expirationDate: productData.expirationDate,
        category: productData.category,
        amount: productData.amount
    }
}

export function mapToProductDto(product: Product): ProductDto {
    return {
        name: product.name,
        manufacturerId: parseInt(String(product.manufacturer.id)),
        manufacturingDate: product.manufacturingDate,
        expirationDate: product.expirationDate,
        categoryId: parseInt(String(product.category.id)),
        amount: product.amount
    }
}