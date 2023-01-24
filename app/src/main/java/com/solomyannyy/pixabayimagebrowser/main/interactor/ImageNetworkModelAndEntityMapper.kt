package com.solomyannyy.pixabayimagebrowser.main.interactor

import com.solomyannyy.pixabayimagebrowser.data.ImageItemDomainModel
import com.solomyannyy.pixabayimagebrowser.main.data.ImageItemEntity

object ImageNetworkModelAndEntityMapper {
    fun mapNetworkModelToEntity(
        model: ImageItemDomainModel,
        query: String,
        oldEntity: ImageItemEntity?
    ): ImageItemEntity {
        val newQuery = if (oldEntity != null) oldEntity.query + ", " + query else query
        return ImageItemEntity(
            imageId = model.id,
            user = model.user,
            tags = model.tags,
            previewUrl = model.previewUrl,
            fullImageUrl = model.fullImageUrl,
            likesCount = model.likesCount,
            downloadsCount = model.downloadsCount,
            commentsCount = model.commentsCount,
            query = newQuery
        )
    }

    fun mapEntityToNetworkModel(entity: ImageItemEntity): ImageItemDomainModel {
        return ImageItemDomainModel(
            id = entity.imageId,
            user = entity.user,
            tags = entity.tags,
            previewUrl = entity.previewUrl,
            fullImageUrl = entity.fullImageUrl,
            likesCount = entity.likesCount,
            downloadsCount = entity.downloadsCount,
            commentsCount = entity.commentsCount
        )
    }
}