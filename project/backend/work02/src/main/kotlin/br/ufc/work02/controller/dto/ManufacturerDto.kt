package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.jpa.Manufacturer

data class ManufacturerDto(
    val id: Long?,
    val name: String
) : GenericDto<Manufacturer> {

    constructor(model: Manufacturer) : this(
        id = model.id,
        name = model.name
    )

    override fun toModel(): Manufacturer {
        return Manufacturer(name = name)
    }
}