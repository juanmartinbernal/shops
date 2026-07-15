package com.juanmartin.data.util

import android.location.Location
import com.juanmartin.core.location.GeoLocation
import com.juanmartin.data.model.Shop

/** Calcula la distancia en km entre la ubicación del usuario y cada comercio. */
object DistanceCalculator {

    fun withDistances(shops: List<Shop>, from: GeoLocation): List<Shop> {
        return shops.mapNotNull { shop ->
            val lat = shop.latitude
            val lng = shop.longitude
            if (lat == null || lng == null) return@mapNotNull null
            val results = FloatArray(1)
            Location.distanceBetween(from.latitude, from.longitude, lat, lng, results)
            shop.copy(distance = results[0] / 1000.0) // metros -> km
        }
    }
}
