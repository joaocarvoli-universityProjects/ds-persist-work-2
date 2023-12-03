package br.ufc.work02.domain.repository.mongo

import br.ufc.work02.domain.model.mongo.Category
import br.ufc.work02.domain.model.mongo.Stock
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("mongoStockRepository")
interface StockMongoRepository : MongoRepository<Stock, String> {

    @Query("{ 'name' : { \$regex: ?0, \$options: 'i' } }")
    fun findAllByName(@Param("name") name: String): List<Stock>

    @Query("{ 'address' : { \$regex: ?0, \$options: 'i' } }")
    fun findAllByAddress(@Param("address") address: String): List<Stock>

    @Query("{ 'cep' : { \$regex: ?0, \$options: 'i' } }")
    fun findAllByCep(@Param("cep") cep: String): List<Stock>

    @Query("{}")
    fun findAllOrderedByField(@Param("field") field: String, sort: Sort): List<Stock>
}
