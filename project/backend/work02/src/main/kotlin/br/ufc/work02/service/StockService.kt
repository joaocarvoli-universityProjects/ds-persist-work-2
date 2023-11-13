package br.ufc.work02.service

import br.ufc.work02.domain.model.Stock
import org.springframework.data.domain.Sort

interface StockService : GenericCrudService<Stock, Long> {
    fun findAllByName(name: String): List<Stock>
    fun findAllByAddress(address: String): List<Stock>
    fun findAllByCep(cep: String): List<Stock>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Stock>
}