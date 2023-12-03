package br.ufc.work02.service

import br.ufc.work02.domain.model.jpa.Category
import org.springframework.data.domain.Sort

interface CategoryService<T, ID> : GenericCrudService<T, ID> {
    fun findAllByName(name: String): List<T>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<T>
}