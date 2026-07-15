package com.juanmartin.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** Comprueba la disponibilidad de conexión a internet. */
interface ConnectivityChecker {
    fun isConnected(): Boolean
}

class ConnectivityCheckerImpl(private val context: Context) : ConnectivityChecker {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
