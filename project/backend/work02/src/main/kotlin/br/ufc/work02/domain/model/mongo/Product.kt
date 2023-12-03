package br.ufc.work02.domain.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("product")
data class Product(
    @Id
    var id: Long? = null,

    var name: String,

    var price: Double,

    @DBRef
    var manufacturer: Manufacturer,

    var manufacturingDate: Date,

    var expirationDate: Date,

    @DBRef
    var category: Category,

    var amount: Int,

    @DBRef
    var stock: Stock
)
