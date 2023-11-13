package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Category
import br.ufc.work02.domain.repository.CategoryRepository
import br.ufc.work02.service.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryServiceImpl(private val productCategoryRepository: CategoryRepository) : CategoryService {
    override fun findAllByName(name: String): List<Category> {
        return productCategoryRepository.findAllByName(name)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Category> {
        val sort: Sort = Sort.by(direction, field)
        return productCategoryRepository.findAllOrderedByField(field, sort)
    }

    @Transactional
    override fun findAll(): List<Category> {
        return productCategoryRepository.findAll()
    }

    @Transactional
    override fun findById(id: Long): Category {
        return productCategoryRepository.getReferenceById(id.toInt())
    }

    @Transactional
    override fun create(model: Category): Category {
        return productCategoryRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Category): Category {
        val productCategory = productCategoryRepository.getReferenceById(id.toInt())

        productCategory.name = model.name

        return productCategoryRepository.save(productCategory)
    }

    @Transactional
    override fun delete(id: Long) {
        productCategoryRepository.deleteById(id.toInt())
    }
}