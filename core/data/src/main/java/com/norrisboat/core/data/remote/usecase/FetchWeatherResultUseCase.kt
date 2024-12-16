package com.norrisboat.core.data.remote.usecase

import com.norrisboat.core.data.remote.repository.WeatherRepository
import com.norrisboat.model.remote.toDomainModel
import com.norrisboat.model.ui.Weather

class FetchWeatherResultUseCase(private val weatherRepository: WeatherRepository) :
    ResultUseCase<Weather, String>() {

    override suspend fun run(params: String): Result<Weather> {
        return weatherRepository.fetchWeatherByCity(params).map { it.toDomainModel() }
    }

}