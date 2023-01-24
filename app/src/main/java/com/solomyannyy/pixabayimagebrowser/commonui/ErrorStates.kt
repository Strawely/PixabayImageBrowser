package com.solomyannyy.pixabayimagebrowser.commonui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.solomyannyy.pixabayimagebrowser.R

@Composable
fun ErrorText() {
    Text(
        stringResource(R.string.main_screen_error_text),
        style = MaterialTheme.typography.h5,
        color = Color.Red
    )
}