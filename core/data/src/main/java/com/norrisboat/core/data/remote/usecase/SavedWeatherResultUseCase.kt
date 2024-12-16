package com.norrisboat.core.data.remote.usecase

import com.norrisboat.core.data.remote.repository.WeatherRepository
import com.norrisboat.model.ui.Weather

class SavedWeatherResultUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<Unit, Weather>() {

    override suspend fun run(params: Weather) {
        return weatherRepository.saveWeather(params)
    }

}