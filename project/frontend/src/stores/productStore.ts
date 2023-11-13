import {defineStore} from "pinia";
import {api} from "../baseConfig.ts";
import {mapToProduct} from "../model/mappers.ts";
import {Product} from "../model/product.ts";
import {ProductDto} from "../views/dto/productDto.ts";


export const useProductStore = defineStore('Product', () => {

    async function getAllProducts(): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data} = await api.get("/product")
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function getAllByName(field: String, name: String): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/name?field=${field}&name=${name}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function getAllByPrice(price: number): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/price?price=${price}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function getAllByPriceRange(priceInitial: number, priceFinal: number): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/priceRange?priceInitial=${priceInitial}&priceFinal=${priceFinal}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function getAllByAmount(amount: number): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/amount?amount=${amount}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function getAllByAmountRange(amountInitial: number, amountFinal: number): Promise<Product[]> {
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/amountRange?amountInitial=${amountInitial}&amountFinal=${amountFinal}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function orderByField(field: String, direction: String){
        let products : Product[] = []
        try {
            const { data } = await api.get(`/product/order?field=${field}&direction=${direction}`)
            products = Object.values(data).map((product : any) => mapToProduct(product))
            return products
        } catch (error){
            return products
        }
    }

    async function createProduct(productDto: ProductDto): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.post(`/product`, productDto)
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function editProductById(productId: number, productDto: ProductDto): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.put(`/product/${productId}`, productDto)
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function removeProductById(productId: number): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }
        try {
            const {status } = await api.delete(`/product/${productId}`)
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    return {
        createProduct, getAllProducts, editProductById, removeProductById, getAllByName,
        getAllByPrice, getAllByPriceRange, getAllByAmount, getAllByAmountRange, orderByField
    }
})