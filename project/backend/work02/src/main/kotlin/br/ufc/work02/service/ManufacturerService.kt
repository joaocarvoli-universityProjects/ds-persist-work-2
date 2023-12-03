package br.ufc.work02.service

import br.ufc.work02.domain.model.jpa.Manufacturer
import org.springframework.data.domain.Sort

interface ManufacturerService<T, ID> : GenericCrudService<T, ID> {
    fun findAllByName(name: String): List<T>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<T>
}