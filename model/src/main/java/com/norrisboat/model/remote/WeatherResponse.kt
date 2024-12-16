package com.norrisboat.model.remote

import com.norrisboat.model.ui.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val location: Location,
    val current: Current
)

@Serializable
data class Location(
    val name: String
)

@Serializable
data class Current(
    @SerialName("temp_c")
    val temp: Double,
    val condition: Condition,
    val humidity: Int,
    val uv: Double,
    @SerialName("feelslike_c")
    val feelsLike: Double
)

@Serializable
data class Condition(
    val text: String,
    val icon: String
)

fun WeatherResponse.toDomainModel() = Weather(
    city = location.name,
    icon = current.condition.icon,
    condition = current.condition.text,
    humidity = current.humidity,
    feelsLike = current.feelsLike,
    temperature = current.temp,
    uvIndex = current.uv
)