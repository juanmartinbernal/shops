package com.juanmartin.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

/** Obtiene la ubicación actual del dispositivo. */
interface LocationProvider {
    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): GeoLocation?
}

class LocationProviderImpl(private val context: Context) : LocationProvider {

    private val fusedClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    override fun isLocationEnabled(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): GeoLocation? {
        val last = fusedClient.lastLocation.await()
        if (last != null) return GeoLocation(last.latitude, last.longitude)
        val fresh = fusedClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .await() ?: return null
        return GeoLocation(fresh.latitude, fresh.longitude)
    }
}
