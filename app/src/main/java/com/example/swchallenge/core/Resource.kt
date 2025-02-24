package com.example.swchallenge.core

sealed class Resource<T>(val data: T? = null, val error: DataError? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(error: DataError, data: T? = null): Resource<T>(data, error)
}