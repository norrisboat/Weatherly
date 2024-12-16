package com.norrisboat.core.data.remote.repository

import com.norrisboat.core.data.local.PreferenceStore
import com.norrisboat.core.data.remote.network.WeatherService
import com.norrisboat.core.data.remote.utils.getResults
import com.norrisboat.model.remote.WeatherResponse
import com.norrisboat.model.ui.Weather
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface WeatherRepository {
    suspend fun fetchWeatherByCity(city: String): Result<WeatherResponse>
    suspend fun saveWeather(weather: Weather)
    suspend fun getWeather(): Flow<Weather>
}

class WeatherRepositoryImpl : KoinComponent, WeatherRepository {

    private val weatherService: WeatherService by inject()
    private val preferenceStore: PreferenceStore by inject()

    override suspend fun fetchWeatherByCity(city: String): Result<WeatherResponse> {
        return weatherService.getWeatherForCity(city)
            .getResults(errorMessage = "Failed to fetch the weather conditions for $city")
    }

    override suspend fun saveWeather(weather: Weather) {
        preferenceStore.setWeather(weather)
    }

    override suspend fun getWeather(): Flow<Weather> {
        return preferenceStore.weather
    }

}