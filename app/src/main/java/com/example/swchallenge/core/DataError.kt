package com.example.swchallenge.core

sealed interface DataError {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        UNKNOWN
    }
}