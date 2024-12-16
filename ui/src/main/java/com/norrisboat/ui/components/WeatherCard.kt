package com.norrisboat.ui.components

import ItemBackgroundColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.norrisboat.core.designsystem.WeatherlyTheme
import com.norrisboat.core.designsystem.dimens
import com.norrisboat.model.ui.Weather
import com.norrisboat.ui.utils.DevicePreviews

@Composable
fun WeatherCard(modifier: Modifier = Modifier, weather: Weather) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ItemBackgroundColor, MaterialTheme.shapes.large)
            .padding(MaterialTheme.dimens.superLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weather.city,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${weather.temperature.toInt()}°",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        AsyncImage(
            model = weather.icon,
            contentDescription = "Humidity Icon",
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
@DevicePreviews
fun WeatherCardPreview() {
    WeatherlyTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            WeatherCard(weather = Weather.sample)
        }
    }
}