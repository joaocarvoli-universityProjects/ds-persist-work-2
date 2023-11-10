package br.ufc.work02.controller

import br.ufc.work02.controller.dto.CategoryDto
import br.ufc.work02.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-category")
class CategoryController(private val productCategoryService: CategoryService) {

    @GetMapping
    fun findAllProductsCategories() : ResponseEntity<List<CategoryDto>> {
        val productsCategories = productCategoryService.findAll()
        val productsCategoriesDto = productsCategories.map { CategoryDto(it) }
        return ResponseEntity.ok(productsCategoriesDto)
    }

    @GetMapping("/{id}")
    fun getProductCategory(@PathVariable id: Long) : ResponseEntity<CategoryDto> {
        val productCategory = productCategoryService.findById(id)
        val productCategoryDto = CategoryDto(productCategory)
        return ResponseEntity.ok(productCategoryDto)
    }

    @PostMapping
    fun createProductCategory(@RequestBody productCategoryDto: CategoryDto) : ResponseEntity<CategoryDto> {
        val productCategory = productCategoryService.create(productCategoryDto.toModel())
        val productCategoryDtoResultant = CategoryDto(productCategory)

        return ResponseEntity.ok(productCategoryDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProductCategory(@PathVariable id: Long, @RequestBody productCategoryDto: CategoryDto) : ResponseEntity<CategoryDto> {
        val productCategory = productCategoryService.update(id, productCategoryDto.toModel())
        val productCategoryDtoResultant = CategoryDto(productCategory)

        return ResponseEntity.ok(productCategoryDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProductCategory(@PathVariable id: Long) {
        productCategoryService.delete(id)
    }
}