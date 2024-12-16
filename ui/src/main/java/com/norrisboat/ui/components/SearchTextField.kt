package com.norrisboat.ui.components

import ItemBackgroundColor
import ItemTitleColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.core.designsystem.WeatherlyTheme
import com.norrisboat.core.designsystem.dimens
import com.norrisboat.ui.R
import com.norrisboat.ui.utils.DevicePreviews

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.default)
            .background(ItemBackgroundColor, MaterialTheme.shapes.large)
            .height(56.dp),
        value = text,
        onValueChange = onTextChanged,
        placeholder = {
            Text(
                text = stringResource(R.string.search_hint),
                fontSize = 14.sp,
                color = ItemTitleColor
            )
        },
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        )
    )
}

@DevicePreviews
@Composable
fun SearchTextFieldPreview() {
    WeatherlyTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SearchTextField(text = "Ghana", onTextChanged = {}, onSearch = {})
        }
    }
}