package com.juanmartin.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juanmartin.core.location.GeoLocation
import com.juanmartin.data.model.Shop
import com.juanmartin.data.util.DistanceCalculator
import java.io.IOException

/** Fuente de datos local: lee los comercios desde assets/shops.json. */
class LocalShopsDataSource(
    private val context: Context,
    private val gson: Gson
) {

    fun getShops(from: GeoLocation): List<Shop> {
        val json = try {
            context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            return emptyList()
        }
        val type = object : TypeToken<List<Shop>>() {}.type
        val shops: List<Shop> = gson.fromJson(json, type) ?: emptyList()
        return DistanceCalculator.withDistances(shops, from)
    }

    private companion object {
        const val FILE_NAME = "shops.json"
    }
}
