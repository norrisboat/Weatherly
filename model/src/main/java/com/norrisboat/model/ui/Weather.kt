package com.norrisboat.model.ui

data class Weather(
    val city: String,
    val temperature: Double,
    val condition: String,
    val icon: String,
    val humidity: Int,
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
