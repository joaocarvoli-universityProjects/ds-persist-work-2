package br.ufc.work02.operations.product

import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.ProductService

interface ProductOperation {
    fun supports(operation: String): Boolean
    fun execute(params: Map<String, String?>, productService: ProductService): List<Product>
}
