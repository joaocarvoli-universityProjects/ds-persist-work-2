package br.ufc.work02.domain.repository

import br.ufc.work02.domain.model.Category
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository : JpaRepository<Category, Int> {
    @Query(value = "SELECT * FROM product_category m WHERE UPPER(m.name) LIKE UPPER(CONCAT('%', :name, '%'))", nativeQuery = true)
    fun findAllByName(@Param("name") name: String): List<Category>

    @Query("select c from product_category c")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Category>
}