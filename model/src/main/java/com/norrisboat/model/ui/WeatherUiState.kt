package com.norrisboat.model.ui

sealed interface WeatherUiState {
    data class SavedWeather(val weather: Weather) : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    data object Idle : WeatherUiState
    data object Empty : WeatherUiState
}
