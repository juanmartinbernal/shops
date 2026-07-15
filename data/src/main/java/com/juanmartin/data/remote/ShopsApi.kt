package com.juanmartin.data.remote

import com.juanmartin.domain.model.Shop
import retrofit2.Response
import retrofit2.http.GET

interface ShopsApi {
    @GET("commerces/public")
    suspend fun fetchShops(): Response<List<Shop>>
}
