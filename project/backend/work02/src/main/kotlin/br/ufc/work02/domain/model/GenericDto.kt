package br.ufc.work02.domain.model

interface GenericDto<T> {
    fun toModel(): T
}