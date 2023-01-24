package com.solomyannyy.pixabayimagebrowser.details.repository

import com.solomyannyy.pixabayimagebrowser.Constants
import com.solomyannyy.pixabayimagebrowser.data.ImageItemDomainModel
import com.solomyannyy.pixabayimagebrowser.data.PixabayApi
import com.solomyannyy.pixabayimagebrowser.details.model.ImageDetailsUiModel
import com.solomyannyy.pixabayimagebrowser.utils.DataState
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class DetailsRepository @Inject constructor(
    private val pixabayApi: PixabayApi,
    @Named(Constants.API_KEY_DEPENDENCY) private val apiKey: String
) {

    fun getImageDetails(imageId: Long) = flow {
        emit(DataState.Loading())
        try {
            val imageDetails = mapToUiModel(pixabayApi.getImageById(imageId, apiKey).hits[0])
            emit(DataState.Success(imageDetails))
        } catch (e: HttpException) {
            emit(DataState.Error(e))
        } catch (e: IOException) {
            emit(DataState.Error(e))
        }
    }

    private fun mapToUiModel(model: ImageItemDomainModel): ImageDetailsUiModel {
        return ImageDetailsUiModel(
            imageUrl = model.fullImageUrl,
            username = model.user,
            tags = model.tags.split(Constants.TAGS_LIST_DELIMITER),
            likesCount = model.likesCount,
            downloadsCount = model.downloadsCount,
            commentsCount = model.commentsCount
        )
    }
}