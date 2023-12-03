package br.ufc.work02.domain.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("manufacturer")
data class Manufacturer(
    @Id
    var id: Long? = null,

    var name: String
)
