package br.ufc.work02.OCP

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Component

@Component
class FindByPriceOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "findByPrice"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val price =
                params["price"]?.toDouble() ?: throw IllegalArgumentException("Price is required")
        return productService.findAllByPrice(price)
    }
}
