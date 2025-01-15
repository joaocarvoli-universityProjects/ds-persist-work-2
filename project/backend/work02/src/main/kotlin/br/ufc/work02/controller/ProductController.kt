package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
import br.ufc.work02.mapper.ProductMapper
import br.ufc.work02.operations.product.ProductOperation
import br.ufc.work02.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
        private val productService: ProductService,
        private val productMapper: ProductMapper,
        private val operations: List<ProductOperation>
) {

    @GetMapping("/operation")
    fun executeOperation(
            @RequestParam operation: String,
            @RequestParam params: Map<String, String?>
    ): ResponseEntity<List<ProductDtoOut>> {
        val strategy =
                operations.find { it.supports(operation) }
                        ?: throw IllegalArgumentException("Operation not supported: $operation")

        val products = strategy.execute(params, productService)
        val productsDto = productMapper.toDtoList(products)
        return ResponseEntity.ok(productsDto)
    }

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAll()
        val productsDto = productMapper.toDtoList(products)
        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id: Long): ResponseEntity<ProductDtoOut> {
        val product = productService.findById(id)
        val productDto = productMapper.toDto(product)
        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun createProduct(@RequestBody productDtoIn: ProductDtoIn): ResponseEntity<ProductDtoOut> {
        val product = productMapper.toEntity(productDtoIn)
        if (product == null) {
            return ResponseEntity.badRequest().build()
        }
        val savedProduct = productService.create(product)
        val productDtoOut = productMapper.toDto(savedProduct)
        return ResponseEntity.ok(productDtoOut)
    }

    @PutMapping("/{id}")
    fun updateProduct(
            @PathVariable id: Long,
            @RequestBody productDtoIn: ProductDtoIn
    ): ResponseEntity<ProductDtoOut> {
        val product = productMapper.toEntity(productDtoIn)
        if (product == null) {
            return ResponseEntity.badRequest().build()
        }
        val updatedProduct = productService.update(id, product)
        val productDtoOut = productMapper.toDto(updatedProduct)
        return ResponseEntity.ok(productDtoOut)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
