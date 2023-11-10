package br.ufc.work02.domain.repository

import br.ufc.work02.domain.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository : JpaRepository<Category, Int>