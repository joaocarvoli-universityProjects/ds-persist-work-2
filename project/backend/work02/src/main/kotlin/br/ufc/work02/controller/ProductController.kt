package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDto
import br.ufc.work02.service.CategoryService
import br.ufc.work02.service.ManufacturerService
import br.ufc.work02.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
        private val productService: ProductService,
        private val productCategoryService: CategoryService,
        private val manufacturerService: ManufacturerService
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
        val product = productService.create(mountProductDto(productDto).toModel())
        val productDtoResultant = ProductDto(product)
        return ResponseEntity.ok(productDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDto: ProductDto) : ResponseEntity<ProductDto> {
        val product = productService.update(id, mountProductDto(productDto).toModel())
        val productDtoResultant = ProductDto(product)
        return ResponseEntity.ok(productDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }

    private fun mountProductDto(productDto: ProductDto) : ProductDto {
        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
        val manufacturer = productDto.manufacturerId?.let { manufacturerService.findById(it) }
        if (productCategory != null && manufacturer != null) {
            productDto.setProductCategory(productCategory)
            productDto.setProductManufacturer(manufacturer)
        }
        return productDto
    }
}