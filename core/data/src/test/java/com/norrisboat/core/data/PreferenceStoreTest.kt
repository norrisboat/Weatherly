package com.norrisboat.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import app.cash.turbine.test
import com.norrisboat.core.data.local.PreferenceStore
import com.norrisboat.core.data.local.PreferenceStoreImpl
import com.norrisboat.model.ui.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File


@OptIn(ExperimentalCoroutinesApi::class)
class PreferenceStoreTest {
    private lateinit var preferenceStore: PreferenceStore
    private lateinit var dataStore: DataStore<Preferences>
    private val testDispatcher = StandardTestDispatcher()
    private val preferences = mutableMapOf<Preferences.Key<*>, Any?>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dataStore = PreferenceDataStoreFactory.create(
            scope = TestScope(testDispatcher),
            produceFile = { File.createTempFile("test_weatherly_preferences", ".preferences_pb") }
        )
        preferenceStore = PreferenceStoreImpl(dataStore)
    }

    @Test
    fun `setWeather should save weather to DataStore`() = runTest(testDispatcher) {
        val testWeather = Weather.sample
        preferenceStore.setWeather(testWeather)
        val savedValue = preferenceStore.weather
        assertEquals(testWeather, savedValue.first())
    }

    @Test
    fun `weather flow should emit Weather object when valid data is present`() =
        runTest(testDispatcher) {
            val testWeather = Weather.sample
            preferenceStore.setWeather(testWeather)

            preferenceStore.weather.test {
                val emittedWeather = awaitItem()
                assertEquals(testWeather, emittedWeather)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `weather flow should emit null when data is invalid`() = runTest(testDispatcher) {
        val key = stringPreferencesKey("key_weather")
        preferences[key] = "invalid_json"

        preferenceStore.weather.test {
            val emittedWeather = awaitItem()
            assertEquals(null, emittedWeather)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `weather flow should emit null when data is absent`() = runTest(testDispatcher) {
        preferenceStore.weather.test {
            val emittedWeather = awaitItem()
            assertEquals(null, emittedWeather)
            cancelAndIgnoreRemainingEvents()
        }
    }
}