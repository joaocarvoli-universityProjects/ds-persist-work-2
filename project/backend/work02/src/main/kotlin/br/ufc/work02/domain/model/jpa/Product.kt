package br.ufc.work02.domain.model.jpa

import jakarta.persistence.*
import java.util.*

@Entity(name = "product")
@NamedQueries(
    NamedQuery(
        name = "Product.findByPrice",
        query = "SELECT p FROM product p WHERE p.price = :price"
    ),
    NamedQuery(
        name = "Product.findByPriceRange",
        query = "SELECT p FROM product p WHERE p.price <= :priceFinal AND p.price >= :priceInitial"
    ),
    NamedQuery(
        name = "Product.findByAmount",
        query = "SELECT p FROM product p WHERE p.amount = :amount"
    ),
    NamedQuery(
        name = "Product.findByAmountRange",
        query = "SELECT p FROM product p WHERE p.amount <= :amountFinal AND p.amount >= :amountInitial"
    )
)
data class Product(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String,

    @Column
    var price: Double,

    @ManyToOne
    var manufacturer: Manufacturer,

    @Column
    @Temporal(TemporalType.DATE)
    var manufacturingDate: Date,

    @Column
    @Temporal(TemporalType.DATE)
    var expirationDate: Date,

    @ManyToOne
    var category: Category,

    @Column
    var amount: Int,

    @ManyToOne
    var stock: Stock
)
