package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductRequestDtoIn
import br.ufc.work02.controller.dto.ProductRequestDtoOut
import br.ufc.work02.service.ProductRequestService
import br.ufc.work02.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-request")
class ProductRequestController(
    private val productService: ProductService,
    private val productRequestService: ProductRequestService
) {

    @GetMapping
    fun findAllProductRequests() : ResponseEntity<List<ProductRequestDtoOut>> {
        val productRequests = productRequestService.findAll()
        val productRequestsDtos = productRequests.map { productRequest ->
            ProductRequestDtoOut(
                id = productRequest.id,
                amount = productRequest.amount,
                product = productRequest.product
            )
        }

        return ResponseEntity.ok(productRequestsDtos)
    }

    @GetMapping("/{id}")
    fun findProductRequestById(@PathVariable id : Long) : ResponseEntity<ProductRequestDtoOut> {
        val productRequest = productRequestService.findById(id)
        val productRequestDto = ProductRequestDtoOut(
            id = productRequest.id,
            amount = productRequest.amount,
            product = productRequest.product
        )

        return ResponseEntity.ok(productRequestDto)
    }

    @PostMapping
    fun createProductRequest(@RequestBody productRequestDtoIn: ProductRequestDtoIn) : ResponseEntity<ProductRequestDtoOut> {
        val product = productRequestDtoIn.productId?.let { productService.findById(it) }

        if (product != null) {
            productRequestDtoIn.setProductN(product)
        }

        val productRequest = productRequestService.create(productRequestDtoIn.toModel())
        val productRequestDto = ProductRequestDtoOut(
            id = productRequest.id,
            amount = productRequest.amount,
            product = productRequest.product
        )

        return ResponseEntity.ok(productRequestDto)
    }

    @PutMapping("/{id}")
    fun updateProductRequest(@PathVariable id: Long, @RequestParam productRequestDtoIn: ProductRequestDtoIn) : ResponseEntity<ProductRequestDtoOut> {
        val product = productRequestDtoIn.productId?.let { productService.findById(it) }

        if (product != null) {
            productRequestDtoIn.setProductN(product)
        }

        val productRequest = productRequestService.update(id, productRequestDtoIn.toModel())
        val productRequestDto = ProductRequestDtoOut(
            id = productRequest.id,
            amount = productRequest.amount,
            product = productRequest.product
        )

        return ResponseEntity.ok(productRequestDto)
    }
}