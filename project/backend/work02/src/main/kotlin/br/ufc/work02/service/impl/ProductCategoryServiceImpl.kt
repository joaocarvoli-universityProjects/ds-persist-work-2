package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.ProductCategory
import br.ufc.work02.domain.repository.ProductCategoryRepository
import br.ufc.work02.service.ProductCategoryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCategoryServiceImpl(private val productCategoryRepository: ProductCategoryRepository) : ProductCategoryService {

    @Transactional
    override fun findAll(): List<ProductCategory> {
        return productCategoryRepository.findAll()
    }

    @Transactional
    override fun findById(id: Long): ProductCategory {
        return productCategoryRepository.getReferenceById(id.toInt())
    }

    @Transactional
    override fun create(model: ProductCategory): ProductCategory {
        return productCategoryRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: ProductCategory): ProductCategory {
        val productCategory = productCategoryRepository.getReferenceById(id.toInt())

        productCategory.name = model.name

        return productCategoryRepository.save(productCategory)
    }

    @Transactional
    override fun delete(id: Long) {
        productCategoryRepository.deleteById(id.toInt())
    }
}