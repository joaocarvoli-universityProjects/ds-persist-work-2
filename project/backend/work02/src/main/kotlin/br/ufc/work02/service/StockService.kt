package br.ufc.work02.service

import br.ufc.work02.domain.model.jpa.Stock
import org.springframework.data.domain.Sort

interface StockService<T, ID> : GenericCrudService<T, ID> {
    fun findAllByName(name: String): List<T>
    fun findAllByAddress(address: String): List<T>
    fun findAllByCep(cep: String): List<T>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<T>
}