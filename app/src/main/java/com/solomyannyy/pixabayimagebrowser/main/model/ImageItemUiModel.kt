package com.solomyannyy.pixabayimagebrowser.main.model

data class ImageItemUiModel(
    val id: Long,
    val username: String,
    val tags: List<String>,
    val previewUrl: String
)
