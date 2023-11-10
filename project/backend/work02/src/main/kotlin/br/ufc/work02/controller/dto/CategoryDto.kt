package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.Category
import br.ufc.work02.domain.model.GenericDto

data class CategoryDto(
        val id: Long?,
        val name: String
) : GenericDto<Category> {
    constructor(model: Category) : this(
            id = model.id,
            name = model.name
    )

    override fun toModel(): Category {
        return Category(name = name)
    }
}
