package br.ufc.work02.service

import br.ufc.work02.domain.model.Manufacturer
import org.springframework.data.domain.Sort

interface ManufacturerOrderingService {
    fun findAllByName(name: String): List<Manufacturer>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Manufacturer>
}