package br.ufc.work02.operations.product

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService
import org.springframework.stereotype.Component

@Component
class FindByNameOperation : ProductOperation {
    override fun supports(operation: String): Boolean = operation == "findByName"

    override fun execute(
            params: Map<String, String?>,
            productService: ProductService
    ): List<Product> {
        val name = params["name"] ?: throw IllegalArgumentException("Name is required")
        val field = params["field"] ?: "name"
        return productService.findAllByFieldName(field, name)
    }
}
