package br.ufc.work02.OCP

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Component

@Component
class FindByPriceRangeOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "findByPriceRange"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val priceInitial =
                params["priceInitial"]?.toDouble()
                        ?: throw IllegalArgumentException("Amount is required")
        val priceFinal =
                params["priceFinal"]?.toDouble()
                        ?: throw IllegalArgumentException("priceFinal is required")
        return productService.findAllByPriceRange(priceInitial, priceFinal)
    }
}
