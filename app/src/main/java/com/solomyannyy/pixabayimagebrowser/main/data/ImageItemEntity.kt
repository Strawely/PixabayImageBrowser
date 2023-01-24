package com.solomyannyy.pixabayimagebrowser.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageItemEntity(
    @PrimaryKey val imageId: Long,
    val user: String,
    val tags: String,
    val previewUrl: String,
    val fullImageUrl: String,
    val likesCount: Int,
    val downloadsCount: Int,
    val commentsCount: Int,
    val query: String
)