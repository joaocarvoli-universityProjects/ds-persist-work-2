package br.ufc.work02.controller

import br.ufc.work02.controller.dto.StockDtoIn
import br.ufc.work02.controller.dto.StockDtoOut
import br.ufc.work02.service.ProductService
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
class StockController(private val stockService: StockService, private val productService: ProductService) {

    @GetMapping
    fun findAllStocks(): ResponseEntity<List<StockDtoOut>> {
        val stocks = stockService.findAll()
        val stocksDto = stocks.map { stock ->
            StockDtoOut(
                id = stock.id,
                name = stock.name,
                address = stock.address,
                cep = stock.cep
            )
        }

        return ResponseEntity.ok(stocksDto)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<StockDtoOut>>{
        val stocks = when(direction){
            "asc" -> stockService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> stockService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
        val stocksDto = stocks.map { stock ->
            StockDtoOut(
                id = stock.id,
                name = stock.name,
                address = stock.address,
                cep = stock.cep
            )
        }
        return ResponseEntity.ok(stocksDto)
    }

    @GetMapping("/{id}")
    fun findStockById(@PathVariable id: Long): ResponseEntity<StockDtoOut> {
        val stock = stockService.findById(id)
        val stockDto = StockDtoOut(
            id = stock.id,
            name = stock.name,
            address = stock.address,
            cep = stock.cep
        )

        return ResponseEntity.ok(stockDto)
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String): ResponseEntity<List<StockDtoOut>> {
        val stocks = stockService.findAllByName(name)
        val stocksDto = stocks.map { stock ->
            StockDtoOut(
                id = stock.id,
                name = stock.name,
                address = stock.address,
                cep = stock.cep
            )
        }

        return ResponseEntity.ok(stocksDto)
    }

    @GetMapping("/address")
    fun findAllByAddress(@RequestParam("address") address: String): ResponseEntity<List<StockDtoOut>> {
        val stocks = stockService.findAllByAddress(address)
        val stocksDto = stocks.map { stock ->
            StockDtoOut(
                id = stock.id,
                name = stock.name,
                address = stock.address,
                cep = stock.cep
            )
        }

        return ResponseEntity.ok(stocksDto)
    }

    @GetMapping("/cep")
    fun findAllByCep(@RequestParam("cep") cep: String): ResponseEntity<List<StockDtoOut>> {
        val stocks = stockService.findAllByCep(cep)
        val stocksDto = stocks.map { stock ->
            StockDtoOut(
                id = stock.id,
                name = stock.name,
                address = stock.address,
                cep = stock.cep
            )
        }

        return ResponseEntity.ok(stocksDto)
    }

    @PostMapping
    fun createStock(@RequestBody stockDto: StockDtoIn): ResponseEntity<StockDtoOut> {
        val stock = stockService.create(stockDto.toModel())
        val stockDtoResultant = StockDtoOut(
            id = stock.id,
            name = stock.name,
            address = stock.address,
            cep = stock.cep
        )

        return ResponseEntity.ok(stockDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateStock(@PathVariable id: Long, @RequestBody stockDto: StockDtoIn): ResponseEntity<StockDtoOut> {
        val stock = stockService.update(id, stockDto.toModel())
        val stockDtoResultant = StockDtoOut(
            id = stock.id,
            name = stock.name,
            address = stock.address,
            cep = stock.cep
        )

        return ResponseEntity.ok(stockDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteStock(@PathVariable id: Long) {
        stockService.delete(id)
    }
}