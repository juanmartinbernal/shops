package com.juanmartin.data.remote

import com.juanmartin.core.error.ErrorCodes
import com.juanmartin.core.location.GeoLocation
import com.juanmartin.core.network.ConnectivityChecker
import com.juanmartin.data.model.Shop
import com.juanmartin.data.util.DistanceCalculator
import java.io.IOException

/** Resultado de una llamada remota: datos o un código de error. */
sealed class RemoteResult {
    data class Success(val shops: List<Shop>) : RemoteResult()
    data class Failure(val errorCode: Int) : RemoteResult()
}

class RemoteShopsDataSource(
    private val api: ShopsApi,
    private val connectivity: ConnectivityChecker
) {

    suspend fun fetchShops(from: GeoLocation): RemoteResult {
        if (!connectivity.isConnected()) {
            return RemoteResult.Failure(ErrorCodes.NO_INTERNET_CONNECTION)
        }
        return try {
            val response = api.fetchShops()
            if (response.isSuccessful) {
                val shops = response.body().orEmpty()
                RemoteResult.Success(DistanceCalculator.withDistances(shops, from))
            } else {
                RemoteResult.Failure(response.code())
            }
        } catch (e: IOException) {
            RemoteResult.Failure(ErrorCodes.NETWORK_ERROR)
        }
    }
}
