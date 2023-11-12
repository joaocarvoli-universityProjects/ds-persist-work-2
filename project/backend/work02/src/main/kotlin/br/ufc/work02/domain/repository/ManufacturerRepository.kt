package br.ufc.work02.domain.repository

import br.ufc.work02.domain.model.Manufacturer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ManufacturerRepository : JpaRepository<Manufacturer, Int> {

    @Query(value = "SELECT * FROM manufacturer m WHERE UPPER(m.name) LIKE UPPER(CONCAT('%', :name, '%'))", nativeQuery = true)
    fun findAllByName(@Param("name") name: String): List<Manufacturer>
}