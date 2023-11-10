package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.ProductRequest
import br.ufc.work02.domain.repository.ProductRequestRepository
import br.ufc.work02.service.ProductRequestService
import org.springframework.stereotype.Service

@Service
class ProductRequestServiceImpl(private val productRequestRepository: ProductRequestRepository) : ProductRequestService {
    override fun findAll(): List<ProductRequest> {
        return productRequestRepository.findAll()
    }

    override fun findById(id: Long): ProductRequest {
        return productRequestRepository.getReferenceById(id.toInt())
    }

    override fun create(model: ProductRequest): ProductRequest {
        return productRequestRepository.save(model)
    }

    override fun update(id: Long, model: ProductRequest): ProductRequest {
        val productRequest = productRequestRepository.getReferenceById(id.toInt())

        productRequest.product = model.product
        productRequest.amount = model.amount

        return productRequestRepository.save(productRequest)
    }

    override fun delete(id: Long) {
        productRequestRepository.deleteById(id.toInt())
    }

}