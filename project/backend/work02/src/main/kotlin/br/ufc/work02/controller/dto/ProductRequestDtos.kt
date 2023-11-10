package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.GenericDto
import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.model.ProductRequest

data class ProductRequestDtoIn(
    val id: Long?,
    val productId: Long?,
    val amount: Int,
) : GenericDto<ProductRequest> {

    lateinit var product : Product

    constructor(model: ProductRequest) : this(
        id = model.id,
        productId = model.product.id,
        amount = model.amount
    )

    fun setProductN(product: Product){
        this.product = product
    }

    override fun toModel(): ProductRequest {
        return ProductRequest(
            id = id,
            product = product,
            amount = amount
        )
    }
}

data class ProductRequestDtoOut(
    val id: Long?,
    val product: Product,
    val amount: Int,
)