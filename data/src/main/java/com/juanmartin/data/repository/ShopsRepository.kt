package com.juanmartin.data.repository

import com.juanmartin.core.common.Resource
import com.juanmartin.core.location.GeoLocation
import com.juanmartin.data.model.Shop
import kotlinx.coroutines.flow.Flow

interface ShopsRepository {
    /** Emite Loading y luego el resultado (remoto, con fallback local). */
    fun getShops(location: GeoLocation): Flow<Resource<List<Shop>>>

    /** Recupera un comercio de la última lista cargada. */
    fun getShopById(id: String): Shop?
}
