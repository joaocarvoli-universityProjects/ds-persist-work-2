package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ManufacturerDto
import br.ufc.work02.service.ManufacturerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manufacturer")
class ManufacturerController(private val manufacturerService: ManufacturerService) {

    @GetMapping
    fun findAllProductsCategories() : ResponseEntity<List<ManufacturerDto>> {
        val manufacturers = manufacturerService.findAll()
        val manufacturerDtos = manufacturers.map { ManufacturerDto(it) }
        return ResponseEntity.ok(manufacturerDtos)
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String) : ResponseEntity<List<ManufacturerDto>> {
        val manufacturers = manufacturerService.findAllByName(name)
        val manufacturerDtos = manufacturers.map { ManufacturerDto(it) }
        return ResponseEntity.ok(manufacturerDtos)
    }

    @GetMapping("/{id}")
    fun getProductCategory(@PathVariable id: Long) : ResponseEntity<ManufacturerDto> {
        val manufacturer = manufacturerService.findById(id)
        val manufacturerDto = ManufacturerDto(manufacturer)
        return ResponseEntity.ok(manufacturerDto)
    }

    @PostMapping
    fun createProductCategory(@RequestBody manufacturerDto: ManufacturerDto) : ResponseEntity<ManufacturerDto> {
        val manufacturer = manufacturerService.create(manufacturerDto.toModel())
        val manufacturerDtoResultant = ManufacturerDto(manufacturer)

        return ResponseEntity.ok(manufacturerDtoResultant)
    }

    @PutMapping("/{id}")
    fun updateProductCategory(@PathVariable id: Long, @RequestBody manufacturerDto: ManufacturerDto) : ResponseEntity<ManufacturerDto> {
        val manufacturer = manufacturerService.update(id, manufacturerDto.toModel())
        val manufacturerDtoResultant = ManufacturerDto(manufacturer)

        return ResponseEntity.ok(manufacturerDtoResultant)
    }

    @DeleteMapping("/{id}")
    fun deleteProductCategory(@PathVariable id: Long) {
        manufacturerService.delete(id)
    }
}