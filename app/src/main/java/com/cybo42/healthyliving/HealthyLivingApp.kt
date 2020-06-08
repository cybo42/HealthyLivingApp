package com.cybo42.healthyliving

import android.app.Application
import com.cybo42.healthyliving.config.Flipper
import com.cybo42.healthyliving.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HealthyLivingApp : Application() {

    val flipper: Flipper by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HealthyLivingApp)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, databaseModule, netModule, apiModule, appModule))
        }

        flipper.init(this)
    }
}
