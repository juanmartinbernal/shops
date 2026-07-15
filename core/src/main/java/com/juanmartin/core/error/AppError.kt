package com.juanmartin.core.error

/** Códigos de error usados en toda la app. */
object ErrorCodes {
    const val NO_INTERNET_CONNECTION = -1
    const val NETWORK_ERROR = -2
    const val DEFAULT_ERROR = -3
    const val SEARCH_ERROR = -104
}

data class AppError(val code: Int, val description: String)
