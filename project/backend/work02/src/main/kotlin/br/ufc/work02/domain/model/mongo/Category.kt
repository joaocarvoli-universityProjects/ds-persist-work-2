package br.ufc.work02.domain.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("category")
data class Category(
    @Id
    var id: Long? = null,

    var name: String
)
