package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductCategoryDto
import br.ufc.work02.service.ProductCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-category")
class ProductCategoryController(private val productCategoryService: ProductCategoryService) {

    @GetMapping
    fun findAllProductsCategories() : ResponseEntity<List<ProductCategoryDto>> {
        val productsCategories = productCategoryService.findAll()
        val productsCategoriesDto = productsCategories.map { ProductCategoryDto(it) }
        return ResponseEntity.ok(productsCategoriesDto)
    }

    @GetMapping("/{id}")
    fun getProductCategory(@PathVariable id: Long) : ResponseEntity<ProductCategoryDto> {
        val productCategory = productCategoryService.findById(id)
        val productCategoryDto = ProductCategoryDto(productCategory)
        return ResponseEntity.ok(productCategoryDto)
    }

    @PostMapping
    fun createProductCategory(@RequestBody productCategoryDto: ProductCategoryDto) : ResponseEntity<ProductCategoryDto> {
        val productCategory = productCategoryService.create(productCategoryDto.toModel())
        val productCategoryDtoResultant = ProductCategoryDto(productCategory)

        return ResponseEntity.ok(productCategoryDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProductCategory(@PathVariable id: Long, @RequestBody productCategoryDto: ProductCategoryDto) : ResponseEntity<ProductCategoryDto> {
        val productCategory = productCategoryService.update(id, productCategoryDto.toModel())
        val productCategoryDtoResultant = ProductCategoryDto(productCategory)

        return ResponseEntity.ok(productCategoryDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProductCategory(@PathVariable id: Long) {
        productCategoryService.delete(id)
    }
}