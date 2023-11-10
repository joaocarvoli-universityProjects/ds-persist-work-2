package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Stock
import br.ufc.work02.domain.repository.StockRepository
import br.ufc.work02.service.StockService
import org.springframework.stereotype.Service

@Service
class StockServiceImpl(private val stockRepository: StockRepository) : StockService {
    override fun findAll(): List<Stock> {
        return stockRepository.findAll()
    }

    override fun findById(id: Long): Stock {
        return stockRepository.getReferenceById(id.toInt())
    }

    override fun create(model: Stock): Stock {
        return stockRepository.save(model)
    }

    override fun update(id: Long, model: Stock): Stock {
        val stock = stockRepository.getReferenceById(id.toInt())

        stock.name = model.name
        stock.products = model.products

        return stockRepository.save(stock)
    }

    override fun delete(id: Long) {
        stockRepository.deleteById(id.toInt())
    }
}