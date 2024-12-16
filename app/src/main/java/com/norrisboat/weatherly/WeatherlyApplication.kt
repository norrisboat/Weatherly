package com.norrisboat.weatherly

import android.app.Application
import com.norrisboat.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherlyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WeatherlyApplication)
        }
    }

}