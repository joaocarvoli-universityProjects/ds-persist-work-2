package br.ufc.work02.service.impl.mongo

import br.ufc.work02.domain.model.mongo.Manufacturer
import br.ufc.work02.domain.repository.mongo.ManufacturerMongoRepository
import br.ufc.work02.service.CategoryService
import br.ufc.work02.service.ManufacturerService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ManufacturerServiceMongoImpl(private val manufacturerMongoRepository: ManufacturerMongoRepository): ManufacturerService<Manufacturer, String> {
    override fun findAllByName(name: String): List<Manufacturer> {
        return manufacturerMongoRepository.findAllByName(name)
    }

    override fun findAllOrderedByField(field: String, direction: Sort.Direction): List<Manufacturer> {
        val sort: Sort = Sort.by(direction, field)
        return manufacturerMongoRepository.findAllOrderedByField(field, sort)
    }

    override fun findAll(): List<Manufacturer> {
        return manufacturerMongoRepository.findAll()
    }

    override fun delete(id: String) {
        manufacturerMongoRepository.deleteById(id)
    }

    override fun update(id: String, model: Manufacturer): Manufacturer {
        val manufacturer = findById(id)

        manufacturer.name = model.name

        return manufacturerMongoRepository.save(manufacturer)    }

    override fun create(model: Manufacturer): Manufacturer {
        return manufacturerMongoRepository.save(model)
    }

    override fun findById(id: String): Manufacturer {
        return manufacturerMongoRepository.findAllById(listOf(id))[0]
    }
}