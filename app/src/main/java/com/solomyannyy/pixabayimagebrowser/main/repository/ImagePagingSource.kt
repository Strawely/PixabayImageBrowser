package com.solomyannyy.pixabayimagebrowser.main.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solomyannyy.pixabayimagebrowser.data.ImageItemDomainModel
import com.solomyannyy.pixabayimagebrowser.main.data.ImageItemDao
import com.solomyannyy.pixabayimagebrowser.main.interactor.ImageNetworkModelAndEntityMapper
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val query: String,
    private val repository: MainRepository,
    private val imageItemDao: ImageItemDao,
    private val isOffline: Boolean
) : PagingSource<Int, ImageItemDomainModel>() {
    private val dbPagingSource = imageItemDao.getImagesByQuery(query)

    override fun getRefreshKey(state: PagingState<Int, ImageItemDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        return state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItemDomainModel> {
        if (isOffline) {
            when (val loadResult = dbPagingSource.load(params)) {
                is LoadResult.Page -> {
                    return LoadResult.Page(
                        data = loadResult.data.map {
                            ImageNetworkModelAndEntityMapper.mapEntityToNetworkModel(it)
                        },
                        prevKey = loadResult.prevKey,
                        nextKey = loadResult.nextKey,
                        itemsBefore = loadResult.itemsBefore,
                        itemsAfter = loadResult.itemsAfter
                    )
                }
                is LoadResult.Error -> Unit
                is LoadResult.Invalid -> Unit
            }

        }
        return try {
            val page = params.key ?: 1
            val response = repository.getSearchResults(query, page)

            val dbEntities = response.map {
                val oldEntity = imageItemDao.getImageById(it.id)
                ImageNetworkModelAndEntityMapper.mapNetworkModelToEntity(it, query, oldEntity)
            }
            imageItemDao.insertAll(dbEntities)

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}