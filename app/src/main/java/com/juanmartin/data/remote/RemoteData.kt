package com.juanmartin.data.remote


import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.error.NETWORK_ERROR
import com.juanmartin.data.error.NO_INTERNET_CONNECTION
import com.juanmartin.data.local.LocalData
import com.juanmartin.data.remote.service.Service
import com.juanmartin.ui.component.shops.entities.ParamFilter
import com.juanmartin.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) :
    RemoteDataSource {
    override suspend fun requestShops(params : ParamFilter, localRepository : LocalData): Resource<Shops> {
        val service = serviceGenerator.createService(Service::class.java)
        return when (val response = processCall(service::fetchShops)) {

            is List<*> -> {
                val result = Shops(response as ArrayList<Shops.ShopsItem>)
                val filter : MutableList<Shops.ShopsItem> = ArrayList()
                val currentLocation = Location("provider")
                currentLocation.latitude = params.latitude
                currentLocation.longitude = params.longitude
                result.shopsList.forEach {
                    if(it.latitude != null && it.longitude != null){
                        val myLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
                        val shopLocationMaps = LatLng(it.latitude, it.longitude)
                        val distance = SphericalUtil.computeDistanceBetween(myLocation, shopLocationMaps);
                        it.distance = distance / 1000 //km
                        filter.add(it)
                    }
                }
                Resource.Success(data = Shops(filter as ArrayList<Shops.ShopsItem>))
            }
            else -> {
                val currentLocation = Location("provider")
                currentLocation.latitude = params.latitude
                currentLocation.longitude = params.longitude
                val list = localRepository.getLocalData(currentLocation)
                return Resource.Success(data = Shops(list as ArrayList<Shops.ShopsItem>))
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
