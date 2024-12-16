package com.norrisboat.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.norrisboat.model.ui.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

interface PreferenceStore {

    suspend fun setWeather(weather: Weather)
    val weather: Flow<Weather>

}

class PreferenceStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceStore {

    private val keyWeather =
        stringPreferencesKey("key_weather")

    override suspend fun setWeather(weather: Weather) {
        dataStore.edit {
            it[keyWeather] = Json.encodeToString(weather)
        }
    }

    override val weather: Flow<Weather> =
        dataStore.data.map { Json.decodeFromString<Weather>(it[keyWeather] ?: "{}") }

}

