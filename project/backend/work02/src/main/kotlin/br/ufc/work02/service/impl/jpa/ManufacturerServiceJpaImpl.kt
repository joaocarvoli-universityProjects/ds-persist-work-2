package br.ufc.work02.service.impl.jpa

import br.ufc.work02.domain.model.jpa.Manufacturer
import br.ufc.work02.domain.repository.jpa.ManufacturerJpaRepository
import br.ufc.work02.service.ManufacturerService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ManufacturerServiceJpaImpl(private val manufacturerRepository: ManufacturerJpaRepository) : ManufacturerService<Manufacturer, Long> {
    override fun findAllByName(name: String): List<Manufacturer> {
        return manufacturerRepository.findAllByName(name)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Manufacturer> {
        val sort: Sort = Sort.by(direction, field)
        return manufacturerRepository.findAllOrderedByField(field, sort)
    }

    override fun findAll(): List<Manufacturer> {
        return manufacturerRepository.findAll()
    }

    override fun findById(id: Long): Manufacturer {
        return manufacturerRepository.getReferenceById(id.toInt())
    }

    override fun create(model: Manufacturer): Manufacturer {
        return manufacturerRepository.save(model)
    }

    override fun update(id: Long, model: Manufacturer): Manufacturer {
        val manufacturer = manufacturerRepository.getReferenceById(id.toInt())

        manufacturer.name = model.name

        return manufacturerRepository.save(manufacturer)
    }

    override fun delete(id: Long) {
        manufacturerRepository.deleteById(id.toInt())
    }
}