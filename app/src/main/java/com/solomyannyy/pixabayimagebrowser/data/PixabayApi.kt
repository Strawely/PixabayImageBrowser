package com.solomyannyy.pixabayimagebrowser.data

import com.solomyannyy.pixabayimagebrowser.BuildConfig.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("key") apiKey: String,
        @Query("per_page") perPage: Int = PER_PAGE
    ): ImageListDomainModel

    @GET("api")
    suspend fun getImageById(
        @Query("id") id: Long,
        @Query("key") apiKey: String
    ): ImageListDomainModel

    companion object {
        const val PER_PAGE = 20
    }
}