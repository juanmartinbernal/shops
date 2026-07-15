package com.juanmartin.data.di

import com.google.gson.Gson
import com.juanmartin.data.local.LocalShopsDataSource
import com.juanmartin.data.remote.RemoteShopsDataSource
import com.juanmartin.data.remote.ShopsApi
import com.juanmartin.data.repository.ShopsRepository
import com.juanmartin.data.repository.ShopsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { Gson() }
    single { get<Retrofit>().create(ShopsApi::class.java) }

    single { RemoteShopsDataSource(get(), get()) }
    single { LocalShopsDataSource(androidContext(), get()) }

    single<ShopsRepository> {
        ShopsRepositoryImpl(get(), get(), Dispatchers.IO)
    }
}
