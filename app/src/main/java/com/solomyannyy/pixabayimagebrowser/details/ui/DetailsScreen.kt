package com.solomyannyy.pixabayimagebrowser.details.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.solomyannyy.pixabayimagebrowser.R
import com.solomyannyy.pixabayimagebrowser.commonui.ErrorText
import com.solomyannyy.pixabayimagebrowser.commonui.TagsList
import com.solomyannyy.pixabayimagebrowser.details.model.ImageDetailsUiModel
import com.solomyannyy.pixabayimagebrowser.details.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(imageId: Long, viewModel: DetailsViewModel) {
    val content by viewModel.contentFlow.collectAsState()
    val isError by viewModel.isErrorFlow.collectAsState()
    val isLoading by viewModel.isLoadingFlow.collectAsState()
    val isDetailedInfoIsVisible by viewModel.detailedInfoIsVisibleFlow.collectAsState()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if (content != null) {
            AsyncImage(
                model = content!!.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { viewModel.onImageClick() },
                placeholder = ColorPainter(Color.LightGray)
            )
            AnimatedVisibility(
                isDetailedInfoIsVisible,
                modifier = Modifier.align(Alignment.BottomStart),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                ImageInfo(contentModel = content!!)
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (isError) {
            ErrorText()
        }
    }

    LaunchedEffect(imageId) { viewModel.updateContent(imageId) }
}

@Composable
private fun ImageInfo(contentModel: ImageDetailsUiModel) {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.5f))) {
        Row(Modifier.padding(4.dp)) {
            DetailInfoItem(iconResId = R.drawable.ic_person_48, text = contentModel.username)
        }

        Row(Modifier.padding(4.dp)) {
            DetailInfoItem(
                iconResId = R.drawable.ic_thumb_up_48,
                text = contentModel.likesCount.toString()
            )
            Spacer(modifier = Modifier.width(48.dp))
            DetailInfoItem(
                iconResId = R.drawable.ic_download_48,
                text = contentModel.downloadsCount.toString()
            )
            Spacer(modifier = Modifier.width(48.dp))
            DetailInfoItem(
                iconResId = R.drawable.ic_comment_48,
                text = contentModel.commentsCount.toString()
            )
        }

        Box(modifier = Modifier.padding(start = 4.dp)) {
            TagsList(contentModel.tags)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun RowScope.DetailInfoItem(@DrawableRes iconResId: Int, text: String) {
    DetailInfoIconImage(resId = iconResId, contentDescription = null)
    Spacer(modifier = Modifier.width(2.dp))
    Text(text)
}

@Composable
fun DetailInfoIconImage(@DrawableRes resId: Int, contentDescription: String?) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(Color.Black),
        modifier = Modifier.size(24.dp)
    )
}

@Preview(widthDp = 240, heightDp = 240)
@Composable
private fun ImageInfoPreview() {
    Box(contentAlignment = Alignment.Center) {
//        ImageInfo(ImageDetailsUiModel.getSampleInstance())
        CircularProgressIndicator()
    }
}
