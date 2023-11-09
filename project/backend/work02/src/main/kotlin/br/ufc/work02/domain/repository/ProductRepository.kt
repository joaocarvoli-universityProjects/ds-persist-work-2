package br.ufc.work02.domain.repository

import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.model.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Int>{
    @Modifying
    @Query("update product p " +
            "set p.name = :name," +
            " p.price = :price," +
            " p.manufacturer = :manufacturer," +
            " p.manufacturingDate = :manufacturingDate," +
            " p.expirationDate = :expirationDate," +
            " p.category = :category " +
            " where p.id = :id")
    fun updateById(
        @Param(value = "id") id: Long,
        @Param(value = "name") name: String?,
        @Param(value = "price") price: Double,
        @Param(value = "manufacturer") manufacturer: String,
        @Param(value = "manufacturingDate") manufacturingDate: Date,
        @Param(value = "expirationDate") expirationDate: Date,
        @Param(value = "category") category: ProductCategory
        )
}