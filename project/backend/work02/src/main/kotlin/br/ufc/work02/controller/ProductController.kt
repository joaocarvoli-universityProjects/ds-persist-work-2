package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAll()
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/name")
    fun findAllByNameField(
        @RequestParam("field") field: String,
        @RequestParam("name") name: String
    ): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByFieldName(field, name)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/price")
    fun findAllByPrice(@RequestParam("price") price: Double): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByPrice(price)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/priceRange")
    fun findAllByPriceRange(
        @RequestParam("priceInitial") priceInitial: Double,
        @RequestParam("priceFinal") priceFinal: Double
    ): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByPriceRange(priceInitial, priceFinal)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/amount")
    fun findAllByAmount(@RequestParam("amount") amount: Int): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByAmount(amount)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/amountRange")
    fun findAllByAmountRange(
        @RequestParam("amountInitial") amountInitial: Int,
        @RequestParam("amountFinal") amountFinal: Int
    ): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByAmountRange(amountInitial, amountFinal)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(
        @RequestParam("field") field: String,
        @RequestParam("direction") direction: String
    ): ResponseEntity<List<ProductDtoOut>> {
        val sortDirection = parseSortDirection(direction)
        val products = productService.findAllOrderedByField(field, sortDirection)
        return ResponseEntity.ok(products.map { toProductDtoOut(it) })
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id: Long): ResponseEntity<ProductDtoOut> {
        val product = productService.findById(id)
        return ResponseEntity.ok(toProductDtoOut(product))
    }

    @PostMapping
    fun createProduct(@RequestBody productDtoIn: ProductDtoIn): ResponseEntity<ProductDtoOut> {
        val product = productService.create(productDtoIn.toModel())
        return ResponseEntity.ok(toProductDtoOut(product))
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody productDtoIn: ProductDtoIn
    ): ResponseEntity<ProductDtoOut> {
        val updatedProduct = productService.update(id, productDtoIn.toModel())
        return ResponseEntity.ok(toProductDtoOut(updatedProduct))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }

    private fun parseSortDirection(direction: String): Sort.Direction {
        return when (direction.lowercase()) {
            "asc" -> Sort.Direction.ASC
            "desc" -> Sort.Direction.DESC
            else -> throw IllegalArgumentException("Invalid sort direction: $direction")
        }
    }

    private fun toProductDtoOut(product: Product): ProductDtoOut {
        return ProductDtoOut(
            id = product.id,
            name = product.name,
            price = product.price,
            manufacturer = product.manufacturer,
            manufacturingDate = product.manufacturingDate,
            expirationDate = product.expirationDate,
            category = product.category,
            amount = product.amount,
            stock = product.stock
        )
    }
}

