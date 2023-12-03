package br.ufc.work02.service.impl.mongo

import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.domain.model.mongo.Manufacturer
import br.ufc.work02.domain.model.mongo.Product
import br.ufc.work02.domain.model.mongo.Stock
import br.ufc.work02.domain.repository.mongo.ProductMongoRepository
import br.ufc.work02.service.ProductService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class ProductServiceMongoImpl(private val productMongoRepository: ProductMongoRepository): ProductService<Product, String> {
    override fun findAllByFieldName(field: String, name: String): List<Product> {
        when(field){
            "Name" -> return productMongoRepository.findByNameContainingIgnoreCase(name)
            "ManufacturerName" -> return productMongoRepository.findByManufacturerNameContainingIgnoreCase(name)
            "CategoryName" -> return productMongoRepository.findByCategoryNameContainingIgnoreCase(name)
            "StockName" -> return productMongoRepository.findByStockNameContainingIgnoreCase(name)
        }
        return listOf()
    }

    override fun findAllByPrice(price: Double): List<Product> {
        return productMongoRepository.findByPrice(price)
    }

    override fun findAllByPriceRange(priceInitial: Double, priceFinal: Double): List<Product> {
        return productMongoRepository.findByPriceRange(priceInitial, priceFinal)
    }

    override fun findAllByAmount(amount: Int): List<Product> {
        return productMongoRepository.findByAmount(amount)
    }

    override fun findAllByAmountRange(amountInitial: Int, amountFinal: Int): List<Product> {
        return productMongoRepository.findByAmountRange(amountInitial, amountFinal)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Product> {
        val sort: Sort = Sort.by(direction, field)

        return when(field){
            "category" -> {
                if(direction == Sort.Direction.ASC) productMongoRepository.findAllCategoryOrderedByAsc(sort)
                else productMongoRepository.findAllCategoryOrderedByDesc(sort)
            }
            "manufacturer" -> {
                if(direction == Sort.Direction.ASC) productMongoRepository.findAllManufacturerOrderedByAsc(sort)
                else productMongoRepository.findAllManufacturerOrderedByDesc(sort)
            }
            else -> productMongoRepository.findAllOrderedByField(field, sort)
        }
    }

    override fun findAll(): List<Product> {
        return productMongoRepository.findAll()
    }

    override fun delete(id: String) {
        productMongoRepository.deleteById(id)
    }

    override fun update(id: String, model: Product): Product {
        val product = findById(id)

        product.name = model.name
        product.price = model.price
        product.manufacturer = model.manufacturer
        product.category = model.category
        product.manufacturingDate = model.manufacturingDate
        product.expirationDate = model.expirationDate
        product.amount = model.amount

        productMongoRepository.updateById(
            id,
            product.name,
            product.price,
            product.manufacturer,
            product.manufacturingDate,
            product.expirationDate,
            product.category,
            product.amount
        )

        return findById(id)
    }

    override fun create(model: Product): Product {
        return productMongoRepository.save(model)
    }

    override fun findById(id: String): Product {
        return productMongoRepository.findAllById(mutableListOf(id)).get(0)
    }
}