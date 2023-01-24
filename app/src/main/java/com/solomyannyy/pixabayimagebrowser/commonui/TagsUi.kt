package com.solomyannyy.pixabayimagebrowser.commonui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TagsList(tags: List<String>, tagText: @Composable (String) -> Unit = { Text(it) }) {
    FlowRow {
        tags.forEach {
            Box(modifier = Modifier.padding(top = 4.dp, end = 4.dp)) {
                Tag(it, tagText)
            }
        }
    }
}

@Composable
fun Tag(tag: String, text: @Composable (String) -> Unit) {
    Box(
        Modifier.border(shape = RoundedCornerShape(24.dp), width = 1.dp, color = Color.Black)
            .padding(6.dp)
    ) {
        text(tag)
    }
}

@Preview(widthDp = 240)
@Composable
private fun TagsListPreview() {
    TagsList(listOf("Tag1", "Tag2", "Tag3", "Tag1", "Tag2", "Tag3"))
}

@Preview
@Composable
private fun TagPreview() {
    Tag(tag = "TagTag") { Text(it) }
}
