package com.solomyannyy.pixabayimagebrowser.main.data

import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ImageItemDao {
    @Query("SELECT * FROM imageItemEntity WHERE imageId=:id")
    suspend fun getImageById(id: Long): ImageItemEntity?

    @Query("SELECT * FROM imageItemEntity WHERE query LIKE :query")
    fun getImagesByQuery(query: String): PagingSource<Int, ImageItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageItemEntities: List<ImageItemEntity>)
}