package br.ufc.work02.service.impl.mongo

import br.ufc.work02.domain.model.mongo.Stock
import br.ufc.work02.domain.repository.mongo.ProductMongoRepository
import br.ufc.work02.domain.repository.mongo.StockMongoRepository
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class StockServiceMongoImpl(private val stockMongoRepository: StockMongoRepository) : StockService<Stock, String> {
    override fun findAllByName(name: String): List<Stock> {
        return stockMongoRepository.findAllByName(name)
    }

    override fun findAllByAddress(address: String): List<Stock> {
        return stockMongoRepository.findAllByAddress(address)
    }

    override fun findAllByCep(cep: String): List<Stock> {
        return stockMongoRepository.findAllByCep(cep)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Stock> {
        val sort: Sort = Sort.by(direction, field)
        return stockMongoRepository.findAllOrderedByField(field, sort)    }

    override fun findAll(): List<Stock> {
        return stockMongoRepository.findAll()
    }

    override fun delete(id: String) {
        stockMongoRepository.deleteById(id)
    }

    override fun update(id: String, model: Stock): Stock {
        val stock = findById(id)

        stock.name = model.name
        stock.address = model.address
        stock.cep = model.cep

        return stockMongoRepository.save(stock)    }

    override fun create(model: Stock): Stock {
        return stockMongoRepository.save(model)
    }

    override fun findById(id: String): Stock {
        return stockMongoRepository.findAllById(mutableListOf(id)).get(0)
    }
}