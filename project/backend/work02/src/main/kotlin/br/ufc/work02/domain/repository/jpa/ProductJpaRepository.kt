package br.ufc.work02.domain.repository.jpa

import br.ufc.work02.domain.model.jpa.Category
import br.ufc.work02.domain.model.jpa.Manufacturer
import br.ufc.work02.domain.model.jpa.Product
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository("jpaProductRepository")
interface ProductJpaRepository : JpaRepository<Product, Int>{


    fun findByNameContainingIgnoreCase(name: String): List<Product>
    fun findByManufacturerNameContainingIgnoreCase(name: String): List<Product>
    fun findByCategoryNameContainingIgnoreCase(name: String): List<Product>
    fun findByStockNameContainingIgnoreCase(name: String): List<Product>
    @Query(name = "Product.findByPrice")
    fun findByPrice(@Param("price") price: Double): List<Product>
    @Query(name = "Product.findByPriceRange")
    fun findByPriceRange(
        @Param("priceInitial") priceInitial: Double,
        @Param("priceFinal") priceFinal: Double
    ): List<Product>

    @Query(name = "Product.findByAmount")
    fun findByAmount(@Param("amount") amount: Int): List<Product>

    @Query(name = "Product.findByAmountRange")
    fun findByAmountRange(
        @Param("amountInitial") amountInitial: Int,
        @Param("amountFinal") amountFinal: Int
    ): List<Product>

    @Modifying
    @Query("update product p " +
            "set p.name = :name," +
            " p.price = :price," +
            " p.manufacturer = :manufacturer," +
            " p.manufacturingDate = :manufacturingDate," +
            " p.expirationDate = :expirationDate," +
            " p.category = :category, " +
            " p.amount = :amount" +
            " where p.id = :id")
    fun updateById(
        @Param(value = "id") id: Long,
        @Param(value = "name") name: String?,
        @Param(value = "price") price: Double,
        @Param(value = "manufacturer") manufacturer: Manufacturer,
        @Param(value = "manufacturingDate") manufacturingDate: Date,
        @Param(value = "expirationDate") expirationDate: Date,
        @Param(value = "category") category: Category,
        @Param(value = "amount") amount: Int
        )

    @Query("select p from product p")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Product>

    @Query("select p from product p order by p.manufacturer.name asc")
    fun findAllManufacturerOrderedByAsc(): List<Product>

    @Query("select p from product p order by p.manufacturer.name desc")
    fun findAllManufacturerOrderedByDesc(): List<Product>

    @Query("select p from product p order by p.category.name asc")
    fun findAllCategoryOrderedByAsc(): List<Product>

    @Query("select p from product p order by p.category.name desc")
    fun findAllCategoryOrderedByDesc(): List<Product>
}