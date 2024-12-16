package com.norrisboat.core.data.remote.network

import com.norrisboat.model.Constants.BASE_URL
import com.norrisboat.model.Constants.KEY
import com.norrisboat.model.remote.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface WeatherService {
    suspend fun getWeatherForCity(city: String): WeatherResponse?

}

class WeatherServiceImpl : KoinComponent, WeatherService {

    private val httpClient: HttpClient by inject()

    override suspend fun getWeatherForCity(city: String): WeatherResponse? {
        return try {
            httpClient.get("$BASE_URL?key=$KEY&q=$city").body()
        } catch (_: Exception) {
            null
        }
    }

}