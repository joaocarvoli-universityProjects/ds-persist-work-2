package br.ufc.work02.domain.model.jpa

import jakarta.persistence.*

@Entity
data class Stock(
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String,

    @Column
    var address: String,

    @Column
    var cep: String
)
