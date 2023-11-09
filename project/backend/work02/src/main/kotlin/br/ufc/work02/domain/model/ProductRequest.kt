package br.ufc.work02.domain.model

import jakarta.persistence.*

@Entity
data class ProductRequest(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    var product: Product,

    @Column
    var productAmount : Int
)
