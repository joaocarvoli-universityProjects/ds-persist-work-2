package br.ufc.work02.OCP

import br.ufc.work02.service.ProductService
import br.ufc.work02.domain.model.Product

interface ProductOperation {
    fun supports(operation: String): Boolean
    fun execute(params: Map<String, String?>, productService: ProductService): List<Product>
}
