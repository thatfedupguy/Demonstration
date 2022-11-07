package com.example.demonstration.utils

sealed class ProgressStatus<T> {
    class Loading<T> : ProgressStatus<T>()
    data class DataSuccess<T>(val data: T?): ProgressStatus<T>()
    data class DataError<T>(val message: String?): ProgressStatus<T>()
}