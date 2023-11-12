import {defineStore} from "pinia";
import {Stock} from "../model/stock.ts";
import {mapToStock} from "../model/mappers.ts";
import {api} from "../baseConfig.ts";

export const useStockStore = defineStore('Stock', () =>{
    async function getAllStocks(): Promise<Stock[]>  {
        let stocks : Stock[] = []
        try {
            const { data } = await api.get("/stock")
            stocks = Object.values(data).map((stock: any) => mapToStock(stock))
            return stocks
        } catch (error){
            return stocks
        }
    }

    async function createStock(stock: Stock): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.post(`/stock`, {
                name: stock.name,
                address: stock.address,
                cep: stock.cep
            })
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function editStockById(stockId: number, stock: Stock): Promise<RequestFeedback> {
        let requestFeedback : RequestFeedback = {
            success : false,
            message : ""
        }

        try {
            const {status } = await api.put(`/stock/${stockId}`, {
                name: stock.name,
                address: stock.address,
                cep: stock.cep
            })
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    async function removeStockById(stockId: number): Promise<RequestFeedback> {
        let requestFeedback: RequestFeedback = {
            success: false,
            message: ""
        }
        try {
            const {status} = await api.delete(`/stock/${stockId}`)
            requestFeedback.success = status == 200;

            return requestFeedback
        } catch (e) {
            requestFeedback.success = false
            return requestFeedback
        }
    }

    return {getAllStocks, createStock, editStockById, removeStockById}
})