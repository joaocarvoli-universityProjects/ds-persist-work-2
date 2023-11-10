package br.ufc.work02.controller

import br.ufc.work02.controller.dto.StockDtoIn
import br.ufc.work02.controller.dto.StockDtoOut
import br.ufc.work02.service.ProductService
import br.ufc.work02.service.StockService
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
                products = stock.products
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
            products = stock.products
        )

        return ResponseEntity.ok(stockDto)
    }

    @PostMapping
    fun createStock(@RequestBody stockDto: StockDtoIn): ResponseEntity<StockDtoOut> {
        val products = stockDto.productsIds.map {
            if (it != null) {
                productService.findById(it)
            } else {
                null
            }
        }

        stockDto.setProducts(products.requireNoNulls())

        val stock = stockService.create(stockDto.toModel())
        val stockDtoResultant = StockDtoOut(
            id = stock.id,
            name = stock.name,
            products = stock.products
        )

        return ResponseEntity.ok(stockDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateStock(@PathVariable id: Long, @RequestBody stockDto: StockDtoIn): ResponseEntity<StockDtoOut> {
        val products = stockDto.productsIds.map {
            if (it != null) {
                productService.findById(it)
            } else {
                null
            }
        }

        stockDto.setProducts(products.requireNoNulls())

        val stock = stockService.update(id, stockDto.toModel())
        val stockDtoResultant = StockDtoOut(
            id = stock.id,
            name = stock.name,
            products = stock.products
        )

        return ResponseEntity.ok(stockDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteStock(@PathVariable id: Long) {
        stockService.delete(id)
    }
}