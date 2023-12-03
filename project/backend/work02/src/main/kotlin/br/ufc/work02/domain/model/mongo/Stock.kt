package br.ufc.work02.domain.model.mongo

import jakarta.persistence.Column
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("stock")
data class Stock(

    @Id
    var id: Long? = null,

    var name: String,

    var address: String,

    var cep: String
)