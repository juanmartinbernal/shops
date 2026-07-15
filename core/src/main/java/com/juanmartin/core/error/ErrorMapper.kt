package com.juanmartin.core.error

import android.content.Context
import com.juanmartin.core.R

/** Traduce códigos de error a mensajes legibles para el usuario. */
interface ErrorMapper {
    fun getError(errorCode: Int): AppError
}

class ErrorMapperImpl(private val context: Context) : ErrorMapper {

    private val messages: Map<Int, Int> = mapOf(
        ErrorCodes.NO_INTERNET_CONNECTION to R.string.no_internet,
        ErrorCodes.NETWORK_ERROR to R.string.network_error,
        ErrorCodes.SEARCH_ERROR to R.string.search_error,
    )

    override fun getError(errorCode: Int): AppError {
        val resId = messages[errorCode] ?: R.string.network_error
        return AppError(code = errorCode, description = context.getString(resId))
    }
}
