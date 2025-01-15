package br.ufc.work02.controller

import br.ufc.work02.controller.dto.CategoryDto
import br.ufc.work02.domain.model.Category
import br.ufc.work02.service.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-category")
class CategoryController(private val productCategoryService: CategoryService) {

    @GetMapping
    fun findAllCategories(): ResponseEntity<List<CategoryDto>> {
        val categories = productCategoryService.findAll()
        return ResponseEntity.ok(categories.map { toCategoryDto(it) })
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String): ResponseEntity<List<CategoryDto>> {
        val categories = productCategoryService.findAllByName(name)
        return ResponseEntity.ok(categories.map { toCategoryDto(it) })
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(
        @RequestParam("field") field: String,
        @RequestParam("direction") direction: String
    ): ResponseEntity<List<CategoryDto>> {
        val sortDirection = parseSortDirection(direction)
        val categories = productCategoryService.findAllOrderedByField(field, sortDirection)
        return ResponseEntity.ok(categories.map { toCategoryDto(it) })
    }

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): ResponseEntity<CategoryDto> {
        val category = productCategoryService.findById(id)
        return ResponseEntity.ok(toCategoryDto(category))
    }

    @PostMapping
    fun createCategory(@RequestBody categoryDto: CategoryDto): ResponseEntity<CategoryDto> {
        val category = productCategoryService.create(categoryDto.toModel())
        return ResponseEntity.ok(toCategoryDto(category))
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody categoryDto: CategoryDto
    ): ResponseEntity<CategoryDto> {
        val updatedCategory = productCategoryService.update(id, categoryDto.toModel())
        return ResponseEntity.ok(toCategoryDto(updatedCategory))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        productCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

    private fun parseSortDirection(direction: String): Sort.Direction {
        return when (direction.lowercase()) {
            "asc" -> Sort.Direction.ASC
            "desc" -> Sort.Direction.DESC
            else -> throw IllegalArgumentException("Invalid sort direction: $direction")
        }
    }

    private fun toCategoryDto(category: Category): CategoryDto {
        return CategoryDto(category)
    }
}