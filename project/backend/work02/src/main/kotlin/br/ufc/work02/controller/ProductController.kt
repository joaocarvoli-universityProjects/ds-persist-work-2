package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ProductDtoIn
import br.ufc.work02.controller.dto.ProductDtoOut
import br.ufc.work02.domain.model.mongo.Manufacturer
import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.domain.model.mongo.Product
import br.ufc.work02.domain.model.mongo.Stock
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
    private val productService: ProductService<Product, String>
) {

    @GetMapping
    fun findAllProducts() : ResponseEntity<List<Product>>{
        val products = productService.findAll()
//        val productsDto = products.map { product ->
//            ProductDtoOut(
//                id = product.id,
//                name = product.name,
//                price = product.price,
//                manufacturer = product.manufacturer,
//                manufacturingDate = product.manufacturingDate,
//                expirationDate = product.expirationDate,
//                category = product.category,
//                amount = product.amount,
//                stock = product.stock
//            )
//        }

        return ResponseEntity.ok(products)
    }

    @GetMapping("name")
    fun findAllByNameField(@RequestParam("field") field: String, @RequestParam("name") name: String): ResponseEntity<List<Product>> {
        val products = productService.findAllByFieldName(field, name)
//        val productsDto = products.map { product ->
//            ProductDtoOut(
//                id = product.id,
//                name = product.name,
//                price = product.price,
//                manufacturer = product.manufacturer,
//                manufacturingDate = product.manufacturingDate,
//                expirationDate = product.expirationDate,
//                category = product.category,
//                amount = product.amount,
//                stock = product.stock
//            )
//        }

        return ResponseEntity.ok(products)
    }

    @GetMapping("price")
    fun findAllByPrice(@RequestParam("price") price: Double): ResponseEntity<List<Product>> {
        val products = productService.findAllByPrice(price)
//        val productsDto = products.map { product ->
//            ProductDtoOut(
//                id = product.id,
//                name = product.name,
//                price = product.price,
//                manufacturer = product.manufacturer,
//                manufacturingDate = product.manufacturingDate,
//                expirationDate = product.expirationDate,
//                category = product.category,
//                amount = product.amount,
//                stock = product.stock
//            )
//        }

        return ResponseEntity.ok(products)
    }

    @GetMapping("priceRange")
    fun findAllByPriceRange(@RequestParam("priceInitial") priceInitial: Double,
                            @RequestParam("priceFinal") priceFinal: Double
                            ): ResponseEntity<List<Product>> {
        val products = productService.findAllByPriceRange(priceInitial, priceFinal)
//        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(products)
    }

    @GetMapping("amount")
    fun findAllByAmount(@RequestParam("amount") amount: Int): ResponseEntity<List<Product>> {
        val products = productService.findAllByAmount(amount)
//        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(products)
    }

    @GetMapping("amountRange")
    fun findAllByAmountRange(@RequestParam("amountInitial") amountInitial: Int,
                             @RequestParam("amountFinal") amountFinal: Int
    ): ResponseEntity<List<Product>> {
        val products = productService.findAllByAmountRange(amountInitial, amountFinal)
//        val productsDto = productsToProductsDtoOut(products)

        return ResponseEntity.ok(products)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<Product>>{
        val products = when(direction){
            "asc" -> productService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> productService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
//        val productsDtos = productsToProductsDtoOut(products)

        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun findProductById(@PathVariable id : String) : ResponseEntity<Product> {
        val product = productService.findById(id)
//        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(product)
    }

    @PostMapping
    fun createProduct(@RequestBody productData: Product) : ResponseEntity<Product> {
        val product = productService.create(productData)
//        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(product)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: String, @RequestBody productData: Product) : ResponseEntity<Product> {
        val product = productService.update(id, productData)
//        val productDto = productToProductDtoOut(product)

        return ResponseEntity.ok(product)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String) {
        productService.delete(id)
    }

//    private fun mountProductDto(productDto: ProductDtoIn) : ProductDtoIn {
//        val productCategory = productDto.categoryId?.let { productCategoryService.findById(it) }
//        val manufacturer = productDto.manufacturerId?.let { manufacturerService.findById(it) }
//        val stock = productDto.stockId?.let { stockService.findById(it) }
//
//        if (productCategory != null && manufacturer != null) {
//            productDto.setProductCategory(productCategory)
//            productDto.setProductManufacturer(manufacturer)
//            if (stock != null) {
//                productDto.setStockN(stock)
//            }
//        }
//        return productDto
//    }
//
//    private fun productsToProductsDtoOut(products: List<Product>): List<ProductDtoOut> {
//        val productsDto = products.map { product ->
//            ProductDtoOut(
//                id = product.id,
//                name = product.name,
//                price = product.price,
//                manufacturer = product.manufacturer,
//                manufacturingDate = product.manufacturingDate,
//                expirationDate = product.expirationDate,
//                category = product.category,
//                amount = product.amount,
//                stock = product.stock
//            )
//        }
//        return productsDto
//    }
//
//    private fun productToProductDtoOut(product: Product): ProductDtoOut {
//        val productDto = ProductDtoOut(
//            id = product.id,
//            name = product.name,
//            price = product.price,
//            manufacturer = product.manufacturer,
//            manufacturingDate = product.manufacturingDate,
//            expirationDate = product.expirationDate,
//            category = product.category,
//            amount = product.amount,
//            stock = product.stock
//        )
//
//        return productDto
//    }
}