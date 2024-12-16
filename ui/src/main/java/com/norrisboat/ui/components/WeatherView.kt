package com.norrisboat.ui.components

import ItemBackgroundColor
import ItemColor
import ItemTitleColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.norrisboat.core.designsystem.WeatherlyTheme
import com.norrisboat.core.designsystem.dimens
import com.norrisboat.model.ui.Weather
import com.norrisboat.ui.R
import com.norrisboat.ui.utils.DevicePreviews

@Composable
fun WeatherView(modifier: Modifier = Modifier, weather: Weather) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.superLarge)
    ) {
        AsyncImage(
            model = weather.icon,
            contentDescription = "Weather Icon",
            modifier = Modifier
                .width(120.dp)
                .padding(MaterialTheme.dimens.default),
            contentScale = ContentScale.Fit
        )

        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.large)) {
            Text(
                text = weather.city,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = "Location Icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
        Text(
            text = "${weather.temperature.toInt()}°",
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .background(
                    color = ItemBackgroundColor,
                    shape = MaterialTheme.shapes.large
                )
                .padding(
                    vertical = MaterialTheme.dimens.superLarge,
                    horizontal = MaterialTheme.dimens.extraLarge
                ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.huge)
        ) {
            WeatherDetailItem(label = "Humidity", value = "${weather.humidity}%")
            WeatherDetailItem(label = "UV", value = weather.uvIndex.toString())
            WeatherDetailItem(label = "Feels Like", value = "${weather.feelsLike.toInt()}°")
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = ItemTitleColor,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            color = ItemColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@DevicePreviews
@Composable
fun WeatherViewPreview() {
    WeatherlyTheme {
        Surface {
            WeatherView(weather = Weather.sample)
        }
    }
}