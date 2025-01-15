package br.ufc.work02.operations.product

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Component

@Component
class FindByAmountOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "findByAmount"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val amount =
                params["amount"]?.toInt() ?: throw IllegalArgumentException("Amount is required")
        return productService.findAllByAmount(amount)
    }
}
