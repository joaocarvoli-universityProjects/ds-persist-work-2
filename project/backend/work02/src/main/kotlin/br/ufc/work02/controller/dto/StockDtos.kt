package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.jpa.Stock

data class StockDtoIn(
    val id: Long?,
    val name: String,
    val address: String,
    val cep: String
) : GenericDto<Stock> {


    constructor(model: Stock) : this(
        id = model.id,
        name = model.name,
        address = model.address,
        cep = model.cep
    )

    override fun toModel(): Stock {
        return Stock(
            name = name, address = address, cep = cep
        )
    }
}

data class StockDtoOut(
    val id: Long?,
    val name: String,
    val address: String,
    val cep: String
)