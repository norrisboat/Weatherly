package com.norrisboat.core.data.remote.usecase

import com.norrisboat.core.data.remote.repository.WeatherRepository
import com.norrisboat.model.ui.Weather
import kotlinx.coroutines.flow.Flow

class GetSavedWeatherResultUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<Flow<Weather?>, Unit>() {

    override suspend fun run(params: Unit): Flow<Weather?> {
        return weatherRepository.getWeather()
    }

}