package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.repository.ProductRepository
import br.ufc.work02.exceptions.EntityNotFoundException
import br.ufc.work02.exceptions.FieldNotFoundException
import br.ufc.work02.exceptions.InvalidPriceOnRangeException
import br.ufc.work02.service.ProductService
import br.ufc.work02.utils.ServiceUtils
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    @Transactional
    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Product> {
        ServiceUtils.validateSortField(field)
        val sort: Sort = Sort.by(direction, field)

        return when (field) {
            "category" -> {
                if (direction == Sort.Direction.ASC) productRepository.findAllCategoryOrderedByAsc()
                else productRepository.findAllCategoryOrderedByDesc()
            }
            "manufacturer" -> {
                if (direction == Sort.Direction.ASC) productRepository.findAllManufacturerOrderedByAsc()
                else productRepository.findAllManufacturerOrderedByDesc()
            }
            else -> productRepository.findAllOrderedByField(field, sort)
        }
    }

    @Transactional
    override fun findAllByFieldName(field: String, name: String): List<Product> {
        return when (field) {
            "Name" -> productRepository.findByNameContainingIgnoreCase(name)
            "ManufacturerName" -> productRepository.findByManufacturerNameContainingIgnoreCase(name)
            "CategoryName" -> productRepository.findByCategoryNameContainingIgnoreCase(name)
            "StockName" -> productRepository.findByStockNameContainingIgnoreCase(name)
            else -> throw FieldNotFoundException("Field '$field' not recognized.")
        }
    }

    @Transactional
    override fun findAllByPrice(price: Double): List<Product> {
        return productRepository.findByPrice(price)
    }

    @Transactional
    override fun findAllByPriceRange(priceInitial: Double, priceFinal: Double): List<Product> {
        if (priceInitial < 0 || priceFinal < 0) throw InvalidPriceOnRangeException("Price range cannot contain negative values.")
        if (priceInitial == 0.0 && priceFinal == 0.0) throw InvalidPriceOnRangeException("Price range cannot be zero.")
        return productRepository.findByPriceRange(priceInitial, priceFinal)
    }

    @Transactional
    override fun findAllByAmount(amount: Int): List<Product> {
        return productRepository.findByAmount(amount)
    }

    @Transactional
    override fun findAllByAmountRange(amountInitial: Int, amountFinal: Int): List<Product> {
        if (amountInitial < 0 || amountFinal < 0) throw InvalidPriceOnRangeException("Amount range cannot contain negative values.")
        if (amountInitial == 0 && amountFinal == 0) throw InvalidPriceOnRangeException("Amount range cannot be zero.")
        return productRepository.findByAmountRange(amountInitial, amountFinal)
    }

    @Transactional
    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    @Transactional
    override fun findById(id: Long): Product {
        return productRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Product with ID $id not found.") }
    }

    @Transactional
    override fun create(model: Product): Product {
        return productRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Product): Product {
        val product = productRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Product with ID $id not found.") }

        product.name = model.name
        product.price = model.price
        product.manufacturer = model.manufacturer
        product.category = model.category
        product.manufacturingDate = model.manufacturingDate
        product.expirationDate = model.expirationDate
        product.amount = model.amount

        return productRepository.save(product)
    }

    @Transactional
    override fun delete(id: Long) {
        if (!productRepository.existsById(id.toInt())) {
            throw EntityNotFoundException("Product with ID $id not found.")
        }
        productRepository.deleteById(id.toInt())
    }
}
