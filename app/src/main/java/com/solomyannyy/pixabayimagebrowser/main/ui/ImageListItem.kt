package com.solomyannyy.pixabayimagebrowser.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solomyannyy.pixabayimagebrowser.R
import com.solomyannyy.pixabayimagebrowser.commonui.TagsList
import com.solomyannyy.pixabayimagebrowser.main.model.ImageItemUiModel

@Composable
internal fun ImageListItem(imageItem: ImageItemUiModel) {
    Row(Modifier.padding(4.dp)) {
        AsyncImage(
            model = imageItem.previewUrl,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            placeholder = ColorPainter(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            UsernameText(imageItem.username)

            Spacer(modifier = Modifier.height(6.dp))

            TagsList(imageItem.tags) { Text(it, style = MaterialTheme.typography.body1) }
        }
    }
}

@Composable
private fun UsernameText(username: String) {
    Row {
        Image(
            painter = painterResource(R.drawable.ic_person_48),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = username)
    }
}

@Composable
private fun TagsListText(tags: String) {
    Text(text = tags)
}

@Preview
@Composable
private fun ImageListItemPreview() {
    ImageListItem(
        imageItem = ImageItemUiModel(
            0,
            "stux",
            listOf("money", "euro", "currency", "tag1", "tag2"),
            "https://cdn.pixabay.com/photo/2016/06/06/09/48/money-1439125_150.jpg"
        )
    )
}
