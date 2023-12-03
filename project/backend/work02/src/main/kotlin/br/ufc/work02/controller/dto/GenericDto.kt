package br.ufc.work02.controller.dto

interface GenericDto<T> {
    fun toModel(): T
}