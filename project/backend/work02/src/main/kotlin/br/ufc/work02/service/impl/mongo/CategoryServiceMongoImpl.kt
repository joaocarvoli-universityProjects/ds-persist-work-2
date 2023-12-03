package br.ufc.work02.service.impl.mongo

import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.domain.repository.mongo.CategoryMongoRepository
import br.ufc.work02.service.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CategoryServiceMongoImpl(private val productCategoryRepository: CategoryMongoRepository): CategoryService<Category, String> {
    override fun findAllByName(name: String): List<Category> {
        return productCategoryRepository.findAllByName(name)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Category> {
        val sort : Sort = Sort.by(direction, field)
        return productCategoryRepository.findAll(sort)
    }

    override fun findAll(): List<Category> {
        return productCategoryRepository.findAll()
    }

    override fun findById(id: String): Category {
        return productCategoryRepository.findAllById(listOf(id))[0]
    }

    override fun create(model: Category): Category {
        return productCategoryRepository.save(model)
    }

    override fun update(id: String, model: Category): Category {
        val producutCategory = findById(id)

        producutCategory.name = model.name

        return productCategoryRepository.save(producutCategory)
    }

    override fun delete(id: String) {
        productCategoryRepository.deleteById(id)
    }
}