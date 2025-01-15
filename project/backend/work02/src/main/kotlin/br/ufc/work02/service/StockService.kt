package br.ufc.work02.service

import br.ufc.work02.domain.model.Stock
import org.springframework.data.domain.Sort

interface StockService : GenericCrudService<Stock, Long>, StockServiceFilterService, StocKServiceOrderingService {}