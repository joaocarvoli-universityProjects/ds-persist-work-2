package br.ufc.work02.domain.repository

import br.ufc.work02.domain.model.Manufacturer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManufacturerRepository : JpaRepository<Manufacturer, Int>