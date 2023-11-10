package br.ufc.work02.domain.model

import jakarta.persistence.*

@Entity
data class Manufacturer(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String
)