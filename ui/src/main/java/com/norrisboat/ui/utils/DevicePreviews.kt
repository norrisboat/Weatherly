package com.norrisboat.ui.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
annotation class DevicePreviews
