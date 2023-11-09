package br.ufc.work02.controller.dto

import br.ufc.work02.domain.model.GenericDto
import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.model.ProductCategory
import java.util.*

data class ProductDto(
        val id: Long?,
        val name: String,
        val price: Double,
        val manufacturer: String,
        val manufacturingDate: Date,
        val expirationDate: Date,
        val categoryId: Long?
) : GenericDto<Product> {

    private lateinit var productCategory : ProductCategory

    constructor(model: Product) : this(
        id = model.id,
        name = model.name,
        price = model.price,
        manufacturer = model.manufacturer,
        manufacturingDate = model.manufacturingDate,
        expirationDate = model.expirationDate,
        categoryId = model.category.id
    )

    fun setProductCategory(productCategory: ProductCategory){
        this.productCategory = productCategory
    }

    override fun toModel(): Product {
        return Product(
            name = name, price = price, manufacturer = manufacturer, manufacturingDate = manufacturingDate,
                expirationDate = expirationDate, category = productCategory
        )
    }
}