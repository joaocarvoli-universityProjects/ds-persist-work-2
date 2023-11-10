package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.repository.ProductRepository
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    @Transactional
    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    @Transactional
    override fun findById(id: Long): Product {
        return productRepository.getReferenceById(id.toInt())
    }

    @Transactional
    override fun create(model: Product): Product {
        return productRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Product): Product {
        val product = productRepository.getReferenceById(id.toInt())

        product.name = model.name
        product.price = model.price
        product.manufacturer = model.manufacturer
        product.category = model.category
        product.manufacturingDate = model.manufacturingDate
        product.expirationDate = model.expirationDate
        product.amount = model.amount

        productRepository.updateById(
            id,
            product.name,
            product.price,
            product.manufacturer,
            product.manufacturingDate,
            product.expirationDate,
            product.category,
            product.amount
        )

        return productRepository.getReferenceById(id.toInt())
    }

    @Transactional
    override fun delete(id: Long) {
        productRepository.deleteById(id.toInt())
    }
}