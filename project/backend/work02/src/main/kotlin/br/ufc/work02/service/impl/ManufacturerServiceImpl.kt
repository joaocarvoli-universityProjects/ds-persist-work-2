package br.ufc.work02.service.impl

import br.ufc.work02.domain.model.Manufacturer
import br.ufc.work02.domain.repository.ManufacturerRepository
import br.ufc.work02.exceptions.EntityNotFoundException
import br.ufc.work02.service.ManufacturerService
import br.ufc.work02.utils.ServiceUtils
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ManufacturerServiceImpl(private val manufacturerRepository: ManufacturerRepository) : ManufacturerService {
    @Transactional
    override fun findAllByName(name: String): List<Manufacturer> {
        return manufacturerRepository.findAllByName(name)
    }

    @Transactional
    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Manufacturer> {
        ServiceUtils.validateSortField(field)
        val sort: Sort = Sort.by(direction, field)
        return manufacturerRepository.findAllOrderedByField(field, sort)
    }

    @Transactional
    override fun findAll(): List<Manufacturer> {
        return manufacturerRepository.findAll()
    }

    @Transactional
    override fun findById(id: Long): Manufacturer {
        return manufacturerRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Manufacturer with ID $id not found.") }
    }

    @Transactional
    override fun create(model: Manufacturer): Manufacturer {
        return manufacturerRepository.save(model)
    }

    @Transactional
    override fun update(id: Long, model: Manufacturer): Manufacturer {
        val manufacturer = manufacturerRepository.findById(id.toInt())
            .orElseThrow { EntityNotFoundException("Manufacturer with ID $id not found.") }

        manufacturer.name = model.name

        return manufacturerRepository.save(manufacturer)
    }

    @Transactional
    override fun delete(id: Long) {
        manufacturerRepository.deleteById(id.toInt())
    }
}