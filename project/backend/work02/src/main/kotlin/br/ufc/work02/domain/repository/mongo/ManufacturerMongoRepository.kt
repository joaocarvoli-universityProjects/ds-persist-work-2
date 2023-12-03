package br.ufc.work02.domain.repository.mongo

import br.ufc.work02.domain.model.mongo.Manufacturer
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("mongoManufacturerRepository")
interface ManufacturerMongoRepository : MongoRepository<Manufacturer, String> {

    @Query("{ 'name' : { \$regex: ?0, \$options: 'i' } }")
    fun findAllByName(@Param("name") name: String): List<Manufacturer>

    @Query("{}")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Manufacturer>
}