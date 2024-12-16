package com.norrisboat.weatherly.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.core.data.viewmodel.WeatherViewModel
import com.norrisboat.core.designsystem.dimens
import com.norrisboat.model.ui.WeatherUiState
import com.norrisboat.ui.R
import com.norrisboat.ui.components.NoCityView
import com.norrisboat.ui.components.SearchTextField
import com.norrisboat.ui.components.WeatherCard
import com.norrisboat.ui.components.WeatherView
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherDisplayScreen(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val viewModel: WeatherViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val search by viewModel.search.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.default),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.large, Alignment.Top)
    ) {
        SearchTextField(text = search,
            onTextChanged = { query ->
                viewModel.updateSearch(query)
            }, onSearch = {
                keyboardController?.hide()
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = MaterialTheme.dimens.superLarge,
                    horizontal = MaterialTheme.dimens.large
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedContent(
                targetState = uiState, label = "uiState"
            ) { state ->
                when (state) {
                    is WeatherUiState.Error -> {
                        Text(
                            text = stringResource(R.string.search_for_city),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            ),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is WeatherUiState.SavedWeather -> {
                        WeatherView(weather = state.weather)
                    }

                    is WeatherUiState.Success -> {
                        WeatherCard(weather = state.weather) {
                            viewModel.selectWeather(state.weather)
                            keyboardController?.hide()
                        }
                    }

                    WeatherUiState.Empty -> {
                        keyboardController?.hide()
                        NoCityView(modifier = Modifier.fillMaxSize(fraction = 0.7f))
                    }

                    WeatherUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    WeatherUiState.Idle -> {}
                }
            }
        }
    }
}