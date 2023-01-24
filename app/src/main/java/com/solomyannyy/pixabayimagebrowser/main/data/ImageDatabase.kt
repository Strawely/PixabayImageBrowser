package com.solomyannyy.pixabayimagebrowser.main.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageItemEntity::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageItemDao(): ImageItemDao
}