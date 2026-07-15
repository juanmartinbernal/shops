package com.juanmartin

import android.app.Application
import com.juanmartin.core.di.coreModule
import com.juanmartin.data.di.dataModule
import com.juanmartin.domain.di.domainModule
import com.juanmartin.feature.shops.di.shopsFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(coreModule, domainModule, dataModule, shopsFeatureModule)
        }
    }
}
