package br.ufc.work02.service

import br.ufc.work02.domain.model.jpa.Product
import org.springframework.data.domain.Sort

interface ProductService<T, ID> : GenericCrudService<T, ID> {
    fun findAllByFieldName(field: String, name: String): List<T>
    fun findAllByPrice(price: Double): List<T>
    fun findAllByPriceRange(priceInitial: Double, priceFinal: Double): List<T>
    fun findAllByAmount(amount: Int): List<T>
    fun findAllByAmountRange(amountInitial: Int, amountFinal: Int): List<T>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<T>
}