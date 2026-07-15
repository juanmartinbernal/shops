package com.juanmartin.data.repository

import com.juanmartin.data.local.LocalShopsDataSource
import com.juanmartin.data.remote.RemoteResult
import com.juanmartin.data.remote.RemoteShopsDataSource
import com.juanmartin.domain.common.Resource
import com.juanmartin.domain.location.GeoLocation
import com.juanmartin.domain.model.Shop
import com.juanmartin.domain.repository.ShopsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShopsRepositoryImpl(
    private val remote: RemoteShopsDataSource,
    private val local: LocalShopsDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ShopsRepository {

    @Volatile
    private var cache: List<Shop> = emptyList()

    override fun getShops(location: GeoLocation): Flow<Resource<List<Shop>>> = flow {
        emit(Resource.Loading)
        val shops = when (val result = remote.fetchShops(location)) {
            is RemoteResult.Success -> result.shops
            is RemoteResult.Failure -> local.getShops(location) // fallback local
        }
        cache = shops
        emit(Resource.Success(shops))
    }.flowOn(ioDispatcher)

    override fun getShopById(id: String): Shop? = cache.firstOrNull { it.id == id }
}
