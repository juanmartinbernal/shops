package com.juanmartin.data.local

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import com.juanmartin.data.dto.comercios.Shops
import java.io.IOException
import javax.inject.Inject

/**
 * Clase para manejar datos locales BD Room, sharedpreferences
 */
class LocalData @Inject constructor(val context: Context) {

    fun getLocalData(location : Location?): List<Shops.ShopsItem> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("shops.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            //AppLogger.d(ioException)
        }
        val filter : MutableList<Shops.ShopsItem> = ArrayList()
        val currentLocation = Location("provider")
        if (location != null) {
            currentLocation.latitude = location.latitude
        }
        if (location != null) {
            currentLocation.longitude = location.longitude
        }
        val response :  List<Shops.ShopsItem> = Gson().fromJson(jsonString,  Array<Shops.ShopsItem>::class.java).toList()
        val result = Shops(response as ArrayList<Shops.ShopsItem>)
        result.shopsList.forEach {
            if(it.latitude != null && it.longitude != null){
                val myLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
                val shopLocationMaps = LatLng(it.latitude, it.longitude)
                val distance = SphericalUtil.computeDistanceBetween(myLocation, shopLocationMaps);
                it.distance = distance / 1000 //km
                filter.add(it)
            }
        }
        return filter
    }
}

