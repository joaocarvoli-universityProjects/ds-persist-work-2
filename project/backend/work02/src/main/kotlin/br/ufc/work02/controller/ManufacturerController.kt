package br.ufc.work02.controller

import br.ufc.work02.controller.dto.ManufacturerDto
import br.ufc.work02.domain.model.Manufacturer
import br.ufc.work02.service.ManufacturerService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manufacturer")
class ManufacturerController(private val manufacturerService: ManufacturerService) {

    @GetMapping
    fun findAllManufacturers(): ResponseEntity<List<ManufacturerDto>> {
        val manufacturers = manufacturerService.findAll()
        return ResponseEntity.ok(manufacturers.map { toManufacturerDto(it) })
    }

    @GetMapping("/name")
    fun findAllByName(@RequestParam("name") name: String): ResponseEntity<List<ManufacturerDto>> {
        val manufacturers = manufacturerService.findAllByName(name)
        return ResponseEntity.ok(manufacturers.map { toManufacturerDto(it) })
    }

    @GetMapping("/order")
    fun orderByFieldByDirection(
        @RequestParam("field") field: String,
        @RequestParam("direction") direction: String
    ): ResponseEntity<List<ManufacturerDto>> {
        val sortDirection = parseSortDirection(direction)
        val manufacturers = manufacturerService.findAllOrderedByField(field, sortDirection)
        return ResponseEntity.ok(manufacturers.map { toManufacturerDto(it) })
    }

    @GetMapping("/{id}")
    fun getManufacturer(@PathVariable id: Long): ResponseEntity<ManufacturerDto> {
        val manufacturer = manufacturerService.findById(id)
        return ResponseEntity.ok(toManufacturerDto(manufacturer))
    }

    @PostMapping
    fun createManufacturer(@RequestBody manufacturerDto: ManufacturerDto): ResponseEntity<ManufacturerDto> {
        val manufacturer = manufacturerService.create(manufacturerDto.toModel())
        return ResponseEntity.ok(toManufacturerDto(manufacturer))
    }

    @PutMapping("/{id}")
    fun updateManufacturer(
        @PathVariable id: Long,
        @RequestBody manufacturerDto: ManufacturerDto
    ): ResponseEntity<ManufacturerDto> {
        val updatedManufacturer = manufacturerService.update(id, manufacturerDto.toModel())
        return ResponseEntity.ok(toManufacturerDto(updatedManufacturer))
    }

    @DeleteMapping("/{id}")
    fun deleteManufacturer(@PathVariable id: Long): ResponseEntity<Void> {
        manufacturerService.delete(id)
        return ResponseEntity.noContent().build()
    }

    private fun parseSortDirection(direction: String): Sort.Direction {
        return when (direction.lowercase()) {
            "asc" -> Sort.Direction.ASC
            "desc" -> Sort.Direction.DESC
            else -> throw IllegalArgumentException("Invalid sort direction: $direction")
        }
    }

    private fun toManufacturerDto(manufacturer: Manufacturer): ManufacturerDto {
        return ManufacturerDto(manufacturer)
    }
}
