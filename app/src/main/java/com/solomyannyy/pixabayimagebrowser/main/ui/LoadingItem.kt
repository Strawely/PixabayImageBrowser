package com.solomyannyy.pixabayimagebrowser.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingItem() {
    Row(Modifier.padding(4.dp)) {
        SkeletonBox(Modifier.size(72.dp))

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            SkeletonBox(Modifier.size(80.dp, 24.dp))

            Spacer(modifier = Modifier.height(8.dp))

            SkeletonBox(Modifier.fillMaxWidth().height(32.dp))
        }
    }
}

@Composable
private fun SkeletonBox(modifier: Modifier) {
    Box(modifier = modifier.background(Color.LightGray))
}

@Preview
@Composable
private fun LoadingItemPreview() {
    LoadingItem()
}
