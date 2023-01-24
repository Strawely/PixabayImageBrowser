package com.solomyannyy.pixabayimagebrowser.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.solomyannyy.pixabayimagebrowser.data.PixabayApi
import com.solomyannyy.pixabayimagebrowser.main.data.ImageDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun pixabayApi(retrofit: Retrofit) = retrofit.create(PixabayApi::class.java)

    @Provides
    @Singleton
    fun provideImageDatabase(@ApplicationContext appContext: Context): ImageDatabase {
        return Room.databaseBuilder(appContext, ImageDatabase::class.java, "image-database")
            .build()
    }

    @Provides
    @Singleton
    fun provideImageDao(imageDb: ImageDatabase) = imageDb.imageItemDao()

    private companion object {
        const val BASE_URL = "https://pixabay.com/"
    }
}