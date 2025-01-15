package br.ufc.work02.controller

import br.ufc.work02.controller.dto.StockDtoIn
import br.ufc.work02.controller.dto.StockDtoOut
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
class StockController(
    private val stockService: StockService,
) {

    @GetMapping
    fun findAllStocks(): ResponseEntity<List<StockDtoOut>> {
        val stocks = stockService.findAll()
        return ResponseEntity.ok(stocks.map { StockDtoOut(it.id, it.name, it.address, it.cep) })
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(
        @Param("field") field: String,
        @Param("direction") direction: String
    ): ResponseEntity<List<StockDtoOut>> {
        val sortDirection = parseSortDirection(direction)
        val stocks = stockService.findAllOrderedByField(field, sortDirection)
        return ResponseEntity.ok(stocks.map { StockDtoOut(it.id, it.name, it.address, it.cep) })
    }

    @GetMapping("/{id}")
    fun findStockById(@PathVariable id: Long): ResponseEntity<StockDtoOut> {
        val stock = stockService.findById(id)
        return ResponseEntity.ok(StockDtoOut(stock.id, stock.name, stock.address, stock.cep))
    }

    @PostMapping
    fun createStock(@RequestBody stockDtoIn: StockDtoIn): ResponseEntity<StockDtoOut> {
        val stock = stockService.create(stockDtoIn.toModel())
        return ResponseEntity.ok(StockDtoOut(stock.id, stock.name, stock.address, stock.cep))
    }

    @PutMapping("/{id}")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody stockDtoIn: StockDtoIn
    ): ResponseEntity<StockDtoOut> {
        val stock = stockService.update(id, stockDtoIn.toModel())
        return ResponseEntity.ok(StockDtoOut(stock.id, stock.name, stock.address, stock.cep))
    }

    @DeleteMapping("/{id}")
    fun deleteStock(@PathVariable id: Long): ResponseEntity<Void> {
        stockService.delete(id)
        return ResponseEntity.noContent().build()
    }

    private fun parseSortDirection(direction: String): Sort.Direction {
        return when (direction.lowercase()) {
            "asc" -> Sort.Direction.ASC
            "desc" -> Sort.Direction.DESC
            else -> throw IllegalArgumentException("Invalid sort direction: $direction")
        }
    }
}
