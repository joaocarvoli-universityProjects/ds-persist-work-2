package br.ufc.work02.service

import br.ufc.work02.domain.model.Product
import org.springframework.data.domain.Sort

interface ProductOrderingService {
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Product>
}
