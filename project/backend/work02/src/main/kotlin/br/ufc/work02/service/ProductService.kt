package br.ufc.work02.service

import br.ufc.work02.domain.model.Product
import org.springframework.data.domain.Sort

interface ProductService : GenericCrudService<Product, Long>, ProductOrderingService, ProductFilterService