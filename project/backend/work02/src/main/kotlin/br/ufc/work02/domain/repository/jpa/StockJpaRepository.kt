package br.ufc.work02.domain.repository.jpa

import br.ufc.work02.domain.model.jpa.Stock
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("jpaStockRepository")
interface StockJpaRepository : JpaRepository<Stock, Int> {
    @Query("SELECT s FROM Stock s WHERE upper(s.name) LIKE upper(concat('%', :name, '%'))")
    fun findAllByName(@Param("name") name: String): List<Stock>


    @Query("SELECT s FROM Stock s WHERE upper(s.address) LIKE upper(concat('%',:address,'%'))")
    fun findAllByAddress(@Param("address") address: String): List<Stock>

    @Query("SELECT s FROM Stock s WHERE upper(s.cep) LIKE upper(concat('%',:cep,'%'))")
    fun findAllByCep(@Param("cep") cep: String): List<Stock>

    @Query("select s from Stock s")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Stock>
}