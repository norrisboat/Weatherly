package com.norrisboat.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.norrisboat.core.data.local.PreferenceStore
import com.norrisboat.core.data.local.PreferenceStoreImpl
import com.norrisboat.core.data.remote.network.WeatherService
import com.norrisboat.core.data.remote.network.WeatherServiceImpl
import com.norrisboat.core.data.remote.repository.WeatherRepository
import com.norrisboat.core.data.remote.repository.WeatherRepositoryImpl
import com.norrisboat.core.data.remote.usecase.FetchWeatherResultUseCase
import com.norrisboat.core.data.remote.usecase.GetSavedWeatherResultUseCase
import com.norrisboat.core.data.remote.usecase.SavedWeatherResultUseCase
import com.norrisboat.core.data.viewmodel.WeatherViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            useCaseModule,
            servicesModule,
            viewModelModule,
            dataStoreModule
        )
    }

}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl() }
}

val useCaseModule = module {
    single { FetchWeatherResultUseCase(get()) }
    single { GetSavedWeatherResultUseCase(get()) }
    single { SavedWeatherResultUseCase(get()) }
}

val servicesModule = module {
    single<WeatherService> { WeatherServiceImpl() }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }
}

val viewModelModule = module {
    viewModelOf(::WeatherViewModel)
}

val dataStoreModule = module {
    single<PreferenceStore> { PreferenceStoreImpl(get()) }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile("weatherly_preferences") }
        )
    }
}