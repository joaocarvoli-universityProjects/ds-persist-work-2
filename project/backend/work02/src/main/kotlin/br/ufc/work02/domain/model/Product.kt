package br.ufc.work02.domain.model

import jakarta.persistence.*
import java.util.*

@Entity(name = "product")
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
    var amount: Int
)
