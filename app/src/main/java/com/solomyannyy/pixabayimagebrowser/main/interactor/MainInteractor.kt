package com.solomyannyy.pixabayimagebrowser.main.interactor

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.net.ConnectivityManagerCompat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.solomyannyy.pixabayimagebrowser.Constants
import com.solomyannyy.pixabayimagebrowser.data.ImageItemDomainModel
import com.solomyannyy.pixabayimagebrowser.main.model.ImageItemUiModel
import com.solomyannyy.pixabayimagebrowser.data.PixabayApi
import com.solomyannyy.pixabayimagebrowser.main.data.ImageItemDao
import com.solomyannyy.pixabayimagebrowser.main.repository.ImagePagingSource
import com.solomyannyy.pixabayimagebrowser.main.repository.MainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val mainRepository: MainRepository,
    @ApplicationContext appContext: Context,
    private val imageItemDao: ImageItemDao
) {
    private val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getImages(query: String) = Pager(
        config = PagingConfig(pageSize = PixabayApi.PER_PAGE),
        pagingSourceFactory = {
            ImagePagingSource(
                query,
                mainRepository,
                imageItemDao,
                isOffline = connectivityManager.activeNetwork == null
            )
        }
    ).flow.map { pagingData -> pagingData.map(::mapToUiModel) }

    private fun mapToUiModel(model: ImageItemDomainModel) = ImageItemUiModel(
        id = model.id,
        username = model.user,
        tags = model.tags.split(Constants.TAGS_LIST_DELIMITER),
        previewUrl = model.previewUrl
    )
}
