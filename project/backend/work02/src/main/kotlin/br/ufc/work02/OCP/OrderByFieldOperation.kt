package br.ufc.work02.OCP

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class OrderByFieldOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "orderBy"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val field = params["field"] ?: throw IllegalArgumentException("Field is required")
        val direction = params["direction"]?.uppercase() ?: "ASC"
        return productService.findAllOrderedByField(field, Sort.Direction.valueOf(direction))
    }
}
