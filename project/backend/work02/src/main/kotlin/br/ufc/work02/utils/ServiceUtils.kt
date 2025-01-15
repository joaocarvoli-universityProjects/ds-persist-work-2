package br.ufc.work02.utils

class ServiceUtils {
    companion object {
        fun validateSortField(field: String) {
            if (field.isBlank()) throw IllegalArgumentException("Sort field must not be blank.")
        }
    }
}