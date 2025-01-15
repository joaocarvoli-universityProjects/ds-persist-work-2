package br.ufc.work02.service

import br.ufc.work02.domain.model.Stock
import org.springframework.data.domain.Sort

interface StocKServiceOrderingService {
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Stock>
}