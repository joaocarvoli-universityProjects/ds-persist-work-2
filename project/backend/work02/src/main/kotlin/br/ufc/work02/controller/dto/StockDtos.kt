package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.GenericDto
import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.model.Stock

data class StockDtoIn(
    val id: Long?,
    val name: String,
    val productsIds: List<Long?>
) : GenericDto<Stock> {

    private lateinit var products : List<Product>

    constructor(model: Stock) : this(
        id = model.id,
        name = model.name,
        productsIds = model.products.map { it.id }
    )

    override fun toModel(): Stock {
        return Stock(
            name = name, products = products
        )
    }

    fun setProducts(products: List<Product>){
        this.products = products
    }
}

data class StockDtoOut(
    val id: Long?,
    val name: String,
    val products: List<Product>
)