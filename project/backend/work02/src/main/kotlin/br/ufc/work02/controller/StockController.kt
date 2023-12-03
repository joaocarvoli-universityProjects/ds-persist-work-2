package br.ufc.work02.controller

import br.ufc.work02.controller.dto.StockDtoIn
import br.ufc.work02.controller.dto.StockDtoOut
import br.ufc.work02.domain.model.mongo.Product
import br.ufc.work02.domain.model.mongo.Stock
import br.ufc.work02.service.ProductService
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
class StockController(private val stockService: StockService<Stock, String>) {

    @GetMapping
    fun findAllStocks(): ResponseEntity<List<Stock>> {
        val stocks = stockService.findAll()
//        val stocksDto = stocks.map { stock ->
//            StockDtoOut(
//                id = stock.id,
//                name = stock.name,
//                address = stock.address,
//                cep = stock.cep
//            )
//        }

        return ResponseEntity.ok(stocks)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<Stock>>{
        val stocks = when(direction){
            "asc" -> stockService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> stockService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
//        val stocksDto = stocks.map { stock ->
//            StockDtoOut(
//                id = stock.id,
//                name = stock.name,
//                address = stock.address,
//                cep = stock.cep
//            )
//        }
        return ResponseEntity.ok(stocks)
    }

    @GetMapping("/{id}")
    fun findStockById(@PathVariable id: String): ResponseEntity<Stock> {
        val stock = stockService.findById(id)
//        val stockDto = StockDtoOut(
//            id = stock.id,
//            name = stock.name,
//            address = stock.address,
//            cep = stock.cep
//        )

        return ResponseEntity.ok(stock)
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String): ResponseEntity<List<Stock>> {
        val stocks = stockService.findAllByName(name)
//        val stocksDto = stocks.map { stock ->
//            StockDtoOut(
//                id = stock.id,
//                name = stock.name,
//                address = stock.address,
//                cep = stock.cep
//            )
//        }

        return ResponseEntity.ok(stocks)
    }

    @GetMapping("/address")
    fun findAllByAddress(@RequestParam("address") address: String): ResponseEntity<List<Stock>> {
        val stocks = stockService.findAllByAddress(address)
//        val stocksDto = stocks.map { stock ->
//            StockDtoOut(
//                id = stock.id,
//                name = stock.name,
//                address = stock.address,
//                cep = stock.cep
//            )
//        }

        return ResponseEntity.ok(stocks)
    }

    @GetMapping("/cep")
    fun findAllByCep(@RequestParam("cep") cep: String): ResponseEntity<List<Stock>> {
        val stocks = stockService.findAllByCep(cep)
//        val stocksDto = stocks.map { stock ->
//            StockDtoOut(
//                id = stock.id,
//                name = stock.name,
//                address = stock.address,
//                cep = stock.cep
//            )
//        }

        return ResponseEntity.ok(stocks)
    }

    @PostMapping
    fun createStock(@RequestBody stockData: Stock): ResponseEntity<Stock> {
        val stock = stockService.create(stockData)
//        val stockDtoResultant = StockDtoOut(
//            id = stock.id,
//            name = stock.name,
//            address = stock.address,
//            cep = stock.cep
//        )

        return ResponseEntity.ok(stock)
    }

    @PutMapping("/{id}")
        fun updateStock(@PathVariable id: String, @RequestBody stockData: Stock): ResponseEntity<Stock> {
        val stock = stockService.update(id, stockData)
//        val stockDtoResultant = StockDtoOut(
//            id = stock.id,
//            name = stock.name,
//            address = stock.address,
//            cep = stock.cep
//        )

        return ResponseEntity.ok(stock)
    }

    @DeleteMapping("/{id}")
    fun deleteStock(@PathVariable id: String) {
        stockService.delete(id)
    }
}