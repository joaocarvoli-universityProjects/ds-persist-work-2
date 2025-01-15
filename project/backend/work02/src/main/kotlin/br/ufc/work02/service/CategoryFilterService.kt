package br.ufc.work02.service

import br.ufc.work02.domain.model.Category

interface CategoryFilterService {
    fun findAllByName(name: String): List<Category>
}