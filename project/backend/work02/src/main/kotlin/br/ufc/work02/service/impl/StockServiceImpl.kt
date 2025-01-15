package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Stock
import br.ufc.work02.domain.repository.StockRepository
import br.ufc.work02.exceptions.EntityNotFoundException
import br.ufc.work02.utils.ServiceUtils
import br.ufc.work02.service.StockService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockServiceImpl(private val stockRepository: StockRepository) : StockService {

    @Transactional
    override fun findAll(): List<Stock> {
        return stockRepository.findAll()
    }

    @Transactional
    override fun findAllByName(name: String): List<Stock> {
        return stockRepository.findAllByName(name)
    }

    @Transactional
    override fun findAllByAddress(address: String): List<Stock> {
        return stockRepository.findAllByAddress(address)
    }

    @Transactional
    override fun findAllByCep(cep: String): List<Stock> {
        return stockRepository.findAllByCep(cep)
    }

    @Transactional
    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Stock> {
        ServiceUtils.validateSortField(field)
        val sort = Sort.by(direction, field)
        return stockRepository.findAllOrderedByField(field, sort)
    }

    @Transactional
    override fun findById(id: Long): Stock {
        return stockRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Stock with ID $id not found.") }
    }

    @Transactional
    override fun create(model: Stock): Stock {
        return stockRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Stock): Stock {
        val stock = stockRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Stock with ID $id not found.") }

        stock.name = model.name
        stock.address = model.address
        stock.cep = model.cep

        return stockRepository.save(stock)
    }

    @Transactional
    override fun delete(id: Long) {
        if (!stockRepository.existsById(id.toInt())) {
            throw EntityNotFoundException("Stock with ID $id not found.")
        }
        stockRepository.deleteById(id.toInt())
    }
}