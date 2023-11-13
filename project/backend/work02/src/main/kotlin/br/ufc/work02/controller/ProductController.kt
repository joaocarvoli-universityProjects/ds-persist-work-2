package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
import br.ufc.work02.domain.model.Product
import br.ufc.work02.service.CategoryService
import br.ufc.work02.service.ManufacturerService
import br.ufc.work02.service.ProductService
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
    private val productCategoryService: CategoryService,
    private val manufacturerService: ManufacturerService,
    private val stockService: StockService
) {

    @GetMapping
    fun findAllProducts() : ResponseEntity<List<ProductDtoOut>>{
        val products = productService.findAll()
        val productsDto = products.map { product ->
            ProductDtoOut(
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

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("name")
    fun findAllByNameField(@RequestParam("field") field: String, @RequestParam("name") name: String): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByFieldName(field, name)
        val productsDto = products.map { product ->
            ProductDtoOut(
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

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("price")
    fun findAllByPrice(@RequestParam("price") price: Double): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByPrice(price)
        val productsDto = products.map { product ->
            ProductDtoOut(
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

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("priceRange")
    fun findAllByPriceRange(@RequestParam("priceInitial") priceInitial: Double,
                            @RequestParam("priceFinal") priceFinal: Double
                            ): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByPriceRange(priceInitial, priceFinal)
        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("amount")
    fun findAllByAmount(@RequestParam("amount") amount: Int): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByAmount(amount)
        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("amountRange")
    fun findAllByAmountRange(@RequestParam("amountInitial") amountInitial: Int,
                             @RequestParam("amountFinal") amountFinal: Int
    ): ResponseEntity<List<ProductDtoOut>> {
        val products = productService.findAllByAmountRange(amountInitial, amountFinal)
        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(productsDto)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<ProductDtoOut>>{
        val products = when(direction){
            "asc" -> productService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> productService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
        val productsDtos = productsToProductsDtoOut(products)

        return ResponseEntity.ok(productsDtos)
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id : Long) : ResponseEntity<ProductDtoOut> {
        val product = productService.findById(id)
        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(productDto)
    }

    @PostMapping
    fun createProduct(@RequestBody productDto: ProductDtoIn) : ResponseEntity<ProductDtoOut> {
        val product = productService.create(mountProductDto(productDto).toModel())
        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(productDto)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDto: ProductDtoIn) : ResponseEntity<ProductDtoOut> {
        val product = productService.update(id, mountProductDto(productDto).toModel())
        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(productDto)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }

    private fun mountProductDto(productDto: ProductDtoIn) : ProductDtoIn {
        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
        val manufacturer = productDto.manufacturerId?.let { manufacturerService.findById(it) }
        val stock = productDto.stockId?.let { stockService.findById(it) }

        if (productCategory != null && manufacturer != null) {
            productDto.setProductCategory(productCategory)
            productDto.setProductManufacturer(manufacturer)
            if (stock != null) {
                productDto.setStockN(stock)
            }
        }
        return productDto
    }

    private fun productsToProductsDtoOut(products: List<Product>): List<ProductDtoOut> {
        val productsDto = products.map { product ->
            ProductDtoOut(
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
        return productsDto
    }

    private fun productToProductDtoOut(product: Product): ProductDtoOut {
        val productDto = ProductDtoOut(
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

        return productDto
    }
}