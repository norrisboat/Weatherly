package com.norrisboat.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val default: Dp = 8.dp,
    val none: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val large: Dp = 12.dp,
    val extraLarge: Dp = 16.dp,
    val superLarge: Dp = 24.dp,
    val huge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current