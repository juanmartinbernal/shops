package com.juanmartin.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juanmartin.data.model.ShopDto
import com.juanmartin.data.model.toDomain
import com.juanmartin.data.util.DistanceCalculator
import com.juanmartin.domain.location.GeoLocation
import com.juanmartin.domain.model.Shop
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
        val type = object : TypeToken<List<ShopDto>>() {}.type
        val dtos: List<ShopDto> = gson.fromJson(json, type) ?: emptyList()
        val shops = dtos.map { it.toDomain() }
        return DistanceCalculator.withDistances(shops, from)
    }

    private companion object {
        const val FILE_NAME = "shops.json"
    }
}
