package com.solomyannyy.pixabayimagebrowser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.solomyannyy.pixabayimagebrowser.R
import com.solomyannyy.pixabayimagebrowser.main.model.ImageItemUiModel
import com.solomyannyy.pixabayimagebrowser.main.viewmodel.MainViewModel

@Composable
internal fun BottomSheetContent(imageItem: ImageItemUiModel?, viewModel: MainViewModel) {
    Column(Modifier.padding(8.dp)) {
        Text(text = stringResource(R.string.details_dialog_title), style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.onDetailsDialogButtonClick(imageItem!!) }
                .padding(vertical = 4.dp),
        ) {
            Image(
                painterResource(R.drawable.ic_open_details_48),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.DarkGray)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = stringResource(R.string.details_dialog_button))
        }
    }
}