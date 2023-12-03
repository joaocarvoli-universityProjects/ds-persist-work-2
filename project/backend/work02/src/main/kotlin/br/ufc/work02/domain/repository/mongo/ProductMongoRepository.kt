package br.ufc.work02.domain.repository.mongo

import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.domain.model.mongo.Manufacturer
import br.ufc.work02.domain.model.mongo.Product
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.web.SortDefault
import org.springframework.stereotype.Repository
import java.util.*

@Repository("mongoProductRepository")
interface ProductMongoRepository : MongoRepository<Product, String> {

    fun findByNameContainingIgnoreCase(name: String): List<Product>
    fun findByManufacturerNameContainingIgnoreCase(name: String): List<Product>
    fun findByCategoryNameContainingIgnoreCase(name: String): List<Product>
    fun findByStockNameContainingIgnoreCase(name: String): List<Product>

    @Query("{ 'price' : ?0 }")
    fun findByPrice(@Param("price") price: Double): List<Product>

    @Query("{ 'price' : { \$gte : ?0, \$lte : ?1 } }")
    fun findByPriceRange(
        @Param("priceInitial") priceInitial: Double,
        @Param("priceFinal") priceFinal: Double
    ): List<Product>

    @Query("{ 'amount' : ?0 }")
    fun findByAmount(@Param("amount") amount: Int): List<Product>

    @Query("{ 'amount' : { \$gte : ?0, \$lte : ?1 } }")
    fun findByAmountRange(
        @Param("amountInitial") amountInitial: Int,
        @Param("amountFinal") amountFinal: Int
    ): List<Product>

    @Query("{'id' : ?0}")
    fun updateById(
        @Param(value = "id") id: String,
        @Param(value = "name") name: String?,
        @Param(value = "price") price: Double,
        @Param(value = "manufacturer") manufacturer: Manufacturer,
        @Param(value = "manufacturingDate") manufacturingDate: Date,
        @Param(value = "expirationDate") expirationDate: Date,
        @Param(value = "category") category: Category,
        @Param(value = "amount") amount: Int
    )

    @Query("{}")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Product>

    @Query("{}")
    fun findAllManufacturerOrderedByAsc(@SortDefault(sort = ["manufacturer.name"], direction = Sort.Direction.ASC) sort: Sort): List<Product>

    @Query("{}")
    fun findAllManufacturerOrderedByDesc(@SortDefault(sort = ["manufacturer.name"], direction = Sort.Direction.DESC) sort: Sort): List<Product>

    @Query("{}")
    fun findAllCategoryOrderedByAsc(@SortDefault(sort = ["category.name"], direction = Sort.Direction.ASC) sort: Sort): List<Product>

    @Query("{}")
    fun findAllCategoryOrderedByDesc(@SortDefault(sort = ["category.name"], direction = Sort.Direction.DESC) sort: Sort): List<Product>
}
