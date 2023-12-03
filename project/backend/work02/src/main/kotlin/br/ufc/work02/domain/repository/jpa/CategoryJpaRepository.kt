package br.ufc.work02.domain.repository.jpa

import br.ufc.work02.domain.model.jpa.Category
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository("jpaCategoryRepository")
interface CategoryJpaRepository : JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM product_category m WHERE UPPER(m.name) LIKE UPPER(CONCAT('%', :name, '%'))", nativeQuery = true)
    fun findAllByName(@Param("name") name: String): List<Category>

    @Query("select c from product_category c")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Category>
}