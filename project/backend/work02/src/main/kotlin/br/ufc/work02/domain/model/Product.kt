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

    @Column
    var manufacturer: String,

    @Column
    @Temporal(TemporalType.TIME)
    var manufacturingDate: Date,

    @Column
    @Temporal(TemporalType.TIME)
    var expirationDate: Date,

    @OneToOne
    var category: Category
)
