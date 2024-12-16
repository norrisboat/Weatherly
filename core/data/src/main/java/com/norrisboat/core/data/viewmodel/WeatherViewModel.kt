package com.norrisboat.core.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norrisboat.core.data.remote.usecase.FetchWeatherResultUseCase
import com.norrisboat.core.data.remote.usecase.GetSavedWeatherResultUseCase
import com.norrisboat.core.data.remote.usecase.SavedWeatherResultUseCase
import com.norrisboat.model.ui.Weather
import com.norrisboat.model.ui.WeatherUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("OPT_IN_USAGE")
class WeatherViewModel : KoinComponent, ViewModel() {

    private val fetchWeatherResultUseCase: FetchWeatherResultUseCase by inject()
    private val savedWeatherResultUseCase: SavedWeatherResultUseCase by inject()
    private val getSavedWeatherResultUseCase: GetSavedWeatherResultUseCase by inject()

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        WeatherUiState.Empty
    )
    private val _search = MutableStateFlow("")

    init {
        observeSavedWeather()

        viewModelScope.launch {
            _search.debounce(300L)
                .collectLatest { city ->
                fetchWeather(city)
            }
        }
    }

    private fun observeSavedWeather() {
        viewModelScope.launch {
            getSavedWeatherResultUseCase.run(Unit)
                .onStart { _uiState.update { WeatherUiState.Loading } }
                .collectLatest { weather ->
                    if (weather == null) {
                        _uiState.update { WeatherUiState.Empty }
                    } else {
                        _uiState.update { WeatherUiState.SavedWeather(weather) }
                    }
                }
        }
    }

    private fun fetchWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchWeatherResultUseCase.run(city)
            result.fold(
                onSuccess = { weather ->
                    _uiState.update { WeatherUiState.Success(weather) }
                },
                onFailure = { error ->
                    _uiState.update { WeatherUiState.Error(error.message ?: "Unknown Error") }
                }
            )
        }
    }

    fun selectWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            savedWeatherResultUseCase.run(weather)
        }
    }

    fun updateSearch(query: String) {
        _search.update { query }
    }
}