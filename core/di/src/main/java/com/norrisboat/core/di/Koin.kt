package com.norrisboat.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.norrisboat.core.data.PreferenceStore
import com.norrisboat.core.data.PreferenceStoreImpl
import org.koin.core.context.startKoin
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

}

val useCaseModule = module {
}

val servicesModule = module {

}

val viewModelModule = module {

}

val dataStoreModule = module {
    single<PreferenceStore> { PreferenceStoreImpl(get()) }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile("weatherly_preferences") }
        )
    }
}