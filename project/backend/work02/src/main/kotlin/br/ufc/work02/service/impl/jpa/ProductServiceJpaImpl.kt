package br.ufc.work02.service.impl.jpa

import br.ufc.work02.domain.model.jpa.Product
import br.ufc.work02.domain.repository.jpa.ProductJpaRepository
import br.ufc.work02.service.ProductService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceJpaImpl(private val productRepository: ProductJpaRepository) : ProductService<Product, Long> {

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Product> {
        val sort: Sort = Sort.by(direction, field)

        return when(field){
            "category" -> {
                if(direction == Sort.Direction.ASC) productRepository.findAllCategoryOrderedByAsc()
                else productRepository.findAllCategoryOrderedByDesc()
            }
            "manufacturer" -> {
                if(direction == Sort.Direction.ASC) productRepository.findAllManufacturerOrderedByAsc()
                else productRepository.findAllManufacturerOrderedByDesc()
            }
            else -> productRepository.findAllOrderedByField(field, sort)
        }
    }

    override fun findAllByFieldName(field: String, name: String): List<Product> {
        when(field){
            "Name" -> return productRepository.findByNameContainingIgnoreCase(name)
            "ManufacturerName" -> return productRepository.findByManufacturerNameContainingIgnoreCase(name)
            "CategoryName" -> return productRepository.findByCategoryNameContainingIgnoreCase(name)
            "StockName" -> return productRepository.findByStockNameContainingIgnoreCase(name)
        }
        return listOf()
    }

    override fun findAllByPrice(price: Double): List<Product> {
        return productRepository.findByPrice(price)
    }

    override fun findAllByPriceRange(priceInitial: Double, priceFinal: Double): List<Product> {
        return productRepository.findByPriceRange(priceInitial, priceFinal)
    }

    override fun findAllByAmount(amount: Int): List<Product> {
        return productRepository.findByAmount(amount)
    }

    override fun findAllByAmountRange(amountInitial: Int, amountFinal: Int): List<Product> {
        return productRepository.findByAmountRange(amountInitial, amountFinal)
    }

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