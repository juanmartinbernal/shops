package com.juanmartin.core.common

/** Contenedor genérico para el resultado de una operación de datos. */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val errorCode: Int) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
