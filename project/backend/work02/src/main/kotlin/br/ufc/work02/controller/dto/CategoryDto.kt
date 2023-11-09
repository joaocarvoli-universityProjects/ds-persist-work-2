package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.GenericDto
import br.ufc.work02.domain.model.ProductCategory

data class ProductCategoryDto(
        val id: Long?,
        val name: String
) : GenericDto<ProductCategory> {
    constructor(model: ProductCategory) : this(
            id = model.id,
            name = model.name
    )

    override fun toModel(): ProductCategory {
        return ProductCategory(name = name)
    }
}
