package br.ufc.work02.service.impl.jpa

import br.ufc.work02.domain.model.jpa.Category
import br.ufc.work02.domain.repository.jpa.CategoryJpaRepository
import br.ufc.work02.service.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryServiceJpaImpl(private val productCategoryRepository: CategoryJpaRepository) : CategoryService<Category, Long> {
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
        return productCategoryRepository.getReferenceById(id)
    }

    @Transactional
    override fun create(model: Category): Category {
        return productCategoryRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Category): Category {
        val productCategory = productCategoryRepository.getReferenceById(id)

        productCategory.name = model.name

        return productCategoryRepository.save(productCategory)
    }

    @Transactional
    override fun delete(id: Long) {
        productCategoryRepository.deleteById(id)
    }
}