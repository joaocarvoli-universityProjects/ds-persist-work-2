package br.ufc.work02.service

import br.ufc.work02.domain.model.Product
import org.springframework.data.domain.Sort

interface ProductService : GenericCrudService<Product, Long> {
//    fun orderByFieldAscending(field: String): List<Product>
//    fun orderByFieldDescending(field: String): List<Product>
    fun findAllByFieldName(field: String, name: String): List<Product>
    fun findAllByPrice(price: Double): List<Product>
    fun findAllByPriceRange(priceInitial: Double, priceFinal: Double): List<Product>
    fun findAllByAmount(amount: Int): List<Product>
    fun findAllByAmountRange(amountInitial: Int, amountFinal: Int): List<Product>
    fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Product>
}