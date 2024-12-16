package com.norrisboat.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val city: String,
    val icon: String,
    val condition: String,
    val humidity: Int,
    val temperature: Double,
    val uvIndex: Double,
    val feelsLike: Double
) {
    companion object {
        val sample = Weather(
            city = "Pune",
            temperature = 45.0,
            condition = "Sunny",
            icon = "http://cdn.weatherapi.com/weather/64x64/day/116.png",
            humidity = 20,
            uvIndex = 4.0,
            feelsLike = 38.0
        )
    }
}
