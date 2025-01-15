package br.ufc.work02.service

import br.ufc.work02.domain.model.Stock

interface StockServiceFilterService {
    fun findAllByName(name: String): List<Stock>
    fun findAllByAddress(address: String): List<Stock>
    fun findAllByCep(cep: String): List<Stock>
}