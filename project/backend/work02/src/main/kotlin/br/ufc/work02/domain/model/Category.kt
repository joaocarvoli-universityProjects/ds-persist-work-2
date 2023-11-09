package br.ufc.work02.domain.model

import jakarta.persistence.*

@Entity(name = "product_category")
data class Category(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String
)
