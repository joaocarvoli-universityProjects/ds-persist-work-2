package br.ufc.work02.OCP

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Component

@Component
class FindByAmountRangeOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "findByAmountRange"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val amountInitial =
                params["amountInitial"]?.toInt()
                        ?: throw IllegalArgumentException("AmountInitial is required")
        val amountFinal =
                params["amountFinal"]?.toInt()
                        ?: throw IllegalArgumentException("AmountFinal is required")
        return productService.findAllByAmountRange(amountInitial, amountFinal)
    }
}
