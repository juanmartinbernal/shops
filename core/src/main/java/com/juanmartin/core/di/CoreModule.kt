package com.juanmartin.core.di

import com.juanmartin.core.error.ErrorMapper
import com.juanmartin.core.error.ErrorMapperImpl
import com.juanmartin.core.location.LocationProvider
import com.juanmartin.core.location.LocationProviderImpl
import com.juanmartin.core.network.ConnectivityChecker
import com.juanmartin.core.network.ConnectivityCheckerImpl
import com.juanmartin.core.network.NetworkConstants
import org.koin.dsl.module

val coreModule = module {
    single { NetworkConstants.okHttpClient() }
    single { NetworkConstants.retrofit(get()) }

    single<ConnectivityChecker> { ConnectivityCheckerImpl(get()) }
    single<LocationProvider> { LocationProviderImpl(get()) }
    single<ErrorMapper> { ErrorMapperImpl(get()) }
}
