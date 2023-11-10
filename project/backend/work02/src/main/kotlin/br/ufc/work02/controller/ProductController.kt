package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
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
    fun findAllProducts() : ResponseEntity<List<ProductDtoOut>>{
        val products = productService.findAll()
        val productsDto = products.map { product ->
            ProductDtoOut(
                id = product.id,
                name = product.name,
                price = product.price,
                manufacturer = product.manufacturer,
                manufacturingDate = product.manufacturingDate,
                expirationDate = product.expirationDate,
                category = product.category,
                amount = product.amount
            )
        }

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id : Long) : ResponseEntity<ProductDtoOut> {
        val product = productService.findById(id)
        val productDto = ProductDtoOut(
            id = product.id,
            name = product.name,
            price = product.price,
            manufacturer = product.manufacturer,
            manufacturingDate = product.manufacturingDate,
            expirationDate = product.expirationDate,
            category = product.category,
            amount = product.amount
        )

        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun createProduct(@RequestBody productDto: ProductDtoIn) : ResponseEntity<ProductDtoOut> {
        val product = productService.create(mountProductDto(productDto).toModel())
        val productDtoResultant = ProductDtoOut(
            id = product.id,
            name = product.name,
            price = product.price,
            manufacturer = product.manufacturer,
            manufacturingDate = product.manufacturingDate,
            expirationDate = product.expirationDate,
            category = product.category,
            amount = product.amount
        )

        return ResponseEntity.ok(productDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDto: ProductDtoIn) : ResponseEntity<ProductDtoOut> {
        val product = productService.update(id, mountProductDto(productDto).toModel())
        val productDtoResultant = ProductDtoOut(
            id = product.id,
            name = product.name,
            price = product.price,
            manufacturer = product.manufacturer,
            manufacturingDate = product.manufacturingDate,
            expirationDate = product.expirationDate,
            category = product.category,
            amount = product.amount
        )

        return ResponseEntity.ok(productDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }

    private fun mountProductDto(productDto: ProductDtoIn) : ProductDtoIn {
        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
        val manufacturer = productDto.manufacturerId?.let { manufacturerService.findById(it) }
        if (productCategory != null && manufacturer != null) {
            productDto.setProductCategory(productCategory)
            productDto.setProductManufacturer(manufacturer)
        }
        return productDto
    }
}