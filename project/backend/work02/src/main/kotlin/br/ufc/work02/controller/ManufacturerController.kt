package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ManufacturerDto
import br.ufc.work02.domain.model.mongo.Manufacturer
import br.ufc.work02.service.ManufacturerService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manufacturer")
class ManufacturerController(private val manufacturerService: ManufacturerService<Manufacturer, String>) {

    @GetMapping
    fun findAllProductsCategories() : ResponseEntity<List<Manufacturer>> {
        val manufacturers = manufacturerService.findAll()
//        val manufacturerDtos = manufacturers.map { ManufacturerDto(it) }
        return ResponseEntity.ok(manufacturers)
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String) : ResponseEntity<List<Manufacturer>> {
        val manufacturers = manufacturerService.findAllByName(name)
//        val manufacturerDtos = manufacturers.map { ManufacturerDto(it) }
        return ResponseEntity.ok(manufacturers)
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(@Param("field") field: String, @Param("direction") direction: String) : ResponseEntity<List<Manufacturer>>{
        val manufacturers = when(direction){
            "asc" -> manufacturerService.findAllOrderedByField(field, Sort.Direction.ASC)
            "desc" -> manufacturerService.findAllOrderedByField(field, Sort.Direction.DESC)
            else -> listOf()
        }
//        val manufacturerDtos = manufacturers.map { ManufacturerDto(it) }

        return ResponseEntity.ok(manufacturers)
    }

    @GetMapping("/{id}")
    fun getProductCategory(@PathVariable id: String) : ResponseEntity<Manufacturer> {
        val manufacturer = manufacturerService.findById(id)
//        val manufacturerDto = ManufacturerDto(manufacturer)
        return ResponseEntity.ok(manufacturer)
    }

    @PostMapping
    fun createProductCategory(@RequestBody manufacturerData: Manufacturer) : ResponseEntity<Manufacturer> {
        val manufacturer = manufacturerService.create(manufacturerData)
//        val manufacturerDtoResultant = ManufacturerDto(manufacturer)

        return ResponseEntity.ok(manufacturer)
    }

    @PutMapping("/{id}")
    fun updateProductCategory(@PathVariable id: String, @RequestBody manufacturerData: Manufacturer) : ResponseEntity<Manufacturer> {
        val manufacturer = manufacturerService.update(id, manufacturerData)
//        val manufacturerDtoResultant = ManufacturerDto(manufacturer)

        return ResponseEntity.ok(manufacturer)
    }

    @DeleteMapping("/{id}")
    fun deleteProductCategory(@PathVariable id: String) {
        manufacturerService.delete(id)
    }
}