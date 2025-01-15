package br.ufc.work02.mapper

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
import br.ufc.work02.domain.model.Product
import br.ufc.work02.domain.model.Category
import br.ufc.work02.domain.model.Manufacturer
import br.ufc.work02.domain.model.Stock
import br.ufc.work02.service.CategoryService
import br.ufc.work02.service.ManufacturerService
import br.ufc.work02.service.StockService
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val categoryService: CategoryService,
    private val manufacturerService: ManufacturerService,
    private val stockService: StockService
) {
    fun toDto(product: Product): ProductDtoOut {
        return ProductDtoOut(
            id = product.id,
            name = product.name,
            price = product.price,
            manufacturer = product.manufacturer,
            manufacturingDate = product.manufacturingDate,
            expirationDate = product.expirationDate,
            category = product.category,
            amount = product.amount,
            stock = product.stock
        )
    }

    fun toDtoList(products: List<Product>): List<ProductDtoOut> {
        return products.map { toDto(it) }
    }

    fun toEntity(productDtoIn: ProductDtoIn): Product? {
        val category = productDtoIn.categoryId?.let { categoryService.findById(it) }
        val manufacturer = productDtoIn.manufacturerId?.let { manufacturerService.findById(it) }
        val stock = productDtoIn.stockId?.let { stockService.findById(it) }
    
        return if (category != null && manufacturer != null && stock != null) {
            Product(
                name = productDtoIn.name,
                price = productDtoIn.price,
                manufacturer = manufacturer,
                category = category,
                stock = stock,
                manufacturingDate = productDtoIn.manufacturingDate,
                expirationDate = productDtoIn.expirationDate,
                amount = productDtoIn.amount
            )
        } else {
            null 
        }
    }
    
}
