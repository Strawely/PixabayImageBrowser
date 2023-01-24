package com.solomyannyy.pixabayimagebrowser.main.repository

import com.solomyannyy.pixabayimagebrowser.Constants.API_KEY_DEPENDENCY
import com.solomyannyy.pixabayimagebrowser.data.ImageItemDomainModel
import com.solomyannyy.pixabayimagebrowser.data.PixabayApi
import com.solomyannyy.pixabayimagebrowser.main.data.ImageItemDao
import com.solomyannyy.pixabayimagebrowser.main.data.ImageItemEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    private val pixabayApi: PixabayApi,
    @Named(API_KEY_DEPENDENCY) private val apiKey: String,
    private val imageItemDao: ImageItemDao
) {
    suspend fun getSearchResults(query: String, page: Int): List<ImageItemDomainModel> {
//        var throwable: Throwable? = null
//        var results = try {
//            pixabayApi.search(query, page, apiKey).hits
//        } catch (e: HttpException) {
//            throwable = e
//            null
//        } catch (e: IOException) {
//            throwable = e
//            null
//        }
//        if (results != null) {
//            val dbEntities = results.map { mapNetworkModelToEntity(it, query) }
//            imageItemDao.insertAll(dbEntities)
//        } else {
//            results = imageItemDao.getImagesByQuery(query).map(::mapEntityToNetworkModel)
//        }
//        if (results == null) throw throwable!!
//        return results
        return pixabayApi.search(query, page, apiKey).hits
    }


}