package br.ufc.work02.controller

import br.ufc.work02.controller.dto.CategoryDto
import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.service.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-category")
class CategoryController(private val productCategoryService: CategoryService<Category, String>) {

    @GetMapping
    fun findAllProductsCategories() : ResponseEntity<List<Category>> {
        val productsCategories = productCategoryService.findAll()
//        val productsCategoriesDto = productsCategories.map { CategoryDto(it) }
        return ResponseEntity.ok(productsCategories)
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String) : ResponseEntity<List<Category>> {
        val productsCategories = productCategoryService.findAllByName(name)
//        val productsCategoriesDto = productsCategories.map { CategoryDto(it) }
        return ResponseEntity.ok(productsCategories)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<Category>>{
        val productsCategories = when(direction){
            "asc" -> productCategoryService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> productCategoryService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
//        val productsCategoriesDto = productsCategories.map { CategoryDto(it) }

        return ResponseEntity.ok(productsCategories)
    }

    @GetMapping("/{id}")
    fun getProductCategory(@PathVariable id: String) : ResponseEntity<Category> {
        val productCategory = productCategoryService.findById(id)
//        val productCategoryDto = CategoryDto(productCategory)
        return ResponseEntity.ok(productCategory)
    }

    @PostMapping
    fun createProductCategory(@RequestBody productCategoryData: Category) : ResponseEntity<Category> {
        val productCategory = productCategoryService.create(productCategoryData)
//        val productCategoryDtoResultant = CategoryDto(productCategory)

        return ResponseEntity.ok(productCategory)
    }

    @PutMapping("/{id}")
    fun updateProductCategory(@PathVariable id: String, @RequestBody productCategoryData: Category) : ResponseEntity<Category> {
        val productCategory = productCategoryService.update(id, productCategoryData)
//        val productCategoryDtoResultant = CategoryDto(productCategory)

        return ResponseEntity.ok(productCategory)
    }

    @DeleteMapping("/{id}")
    fun deleteProductCategory(@PathVariable id: String) {
        productCategoryService.delete(id)
    }
}