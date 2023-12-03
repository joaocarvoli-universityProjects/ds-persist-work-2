package br.ufc.work02.domain.repository.mongo

import br.ufc.work02.domain.model.mongo.Category
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("mongoCategoryRepository")
interface CategoryMongoRepository : MongoRepository<Category, String> {

    @Query("{ 'name' : { \$regex: ?0, \$options: 'i' } }")
    fun findAllByName(@Param("name") name: String): List<Category>

    @Query("{}")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Category>
}