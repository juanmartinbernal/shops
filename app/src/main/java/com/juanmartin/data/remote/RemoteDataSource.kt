package com.juanmartin.data.remote

import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.local.LocalData
import com.juanmartin.ui.component.shops.entities.ParamFilter


/**
 * Created by Juan Martín Bernal
 */

internal interface RemoteDataSource {
    suspend fun requestShops(params : ParamFilter, localRepository : LocalData): Resource<Shops>
}
