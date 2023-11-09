package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDto
import br.ufc.work02.service.ProductCategoryService
import br.ufc.work02.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
        private val productService: ProductService,
        private val productCategoryService: ProductCategoryService
) {

    @GetMapping
    fun findAllProducts() : ResponseEntity<List<ProductDto>>{
        val products = productService.findAll()
        val productsDto = products.map { ProductDto(it) }
        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id : Long) : ResponseEntity<ProductDto> {
        val product = productService.findById(id)
        val productDto = ProductDto(product)
        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun createProduct(@RequestBody productDto: ProductDto) : ResponseEntity<ProductDto> {
        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
        if (productCategory != null) {
            productDto.setProductCategory(productCategory)
        }

        val product = productService.create(productDto.toModel())
        val productDtoResultant = ProductDto(product)
        return ResponseEntity.ok(productDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDto: ProductDto) : ResponseEntity<ProductDto> {
        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
        if (productCategory != null) {
            productDto.setProductCategory(productCategory)
        }

        val product = productService.update(id, productDto.toModel())
        val productDtoResultant = product?.let { ProductDto(it) }
        return ResponseEntity.ok(productDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }
}