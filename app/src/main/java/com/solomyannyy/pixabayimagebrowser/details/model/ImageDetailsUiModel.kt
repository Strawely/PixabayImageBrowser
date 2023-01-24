package com.solomyannyy.pixabayimagebrowser.details.model

data class ImageDetailsUiModel(
    val imageUrl: String,
    val username: String,
    val tags: List<String>,
    val likesCount: Int,
    val downloadsCount: Int,
    val commentsCount: Int
)
