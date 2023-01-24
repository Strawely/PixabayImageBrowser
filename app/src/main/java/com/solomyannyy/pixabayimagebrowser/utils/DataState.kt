package com.solomyannyy.pixabayimagebrowser.utils

sealed interface DataState<T> {
    class Loading<T> : DataState<T>
    class Error<T>(val throwable: Throwable) : DataState<T>
    class Success<T>(val data: T) : DataState<T>
}