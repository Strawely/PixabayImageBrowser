package com.solomyannyy.pixabayimagebrowser.data

import com.squareup.moshi.Json

data class ImageItemDomainModel(
    val id: Long,
    val user: String,
    val tags: String,
    @Json(name = "previewURL") val previewUrl: String,
    @Json(name = "largeImageURL") val fullImageUrl: String,
    @Json(name = "likes") val likesCount: Int,
    @Json(name = "downloads") val downloadsCount: Int,
    @Json(name = "comments") val commentsCount: Int,
)
