package com.test.musicfestival.util

sealed class Resource<T>(
    open val data: T? = null,
    open val message: String = "",
    open val errorType: ErrorType = ErrorType.UNKNOWN
) {
    class Loading<T>() : Resource<T>()

    data class Success<T>(override val data: T?, override val message: String = "") : Resource<T>()

    data class Error<T>(
        override val errorType: ErrorType = ErrorType.UNKNOWN,
        override val message: String = errorType.errorMessage
    ) : Resource<T>()
}

enum class ErrorType(val errorMessage: String) {
    UNKNOWN(errorMessage = "Error"),
    EMPTY_DATA("No data. Please try after some time."),
    NO_INTERNET("No internet connectivity."),
    TIME_OUT("Time Out"),
    THROTTLING("Too many requests. Please try again after some time.")
}
