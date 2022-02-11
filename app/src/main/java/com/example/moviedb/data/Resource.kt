package com.example.moviedb.data

import kotlinx.coroutines.flow.flow
import timber.log.Timber

sealed interface Resource<out T> {
    companion object {
        fun <T> fromSourceFlow(sourceCall: suspend () -> T) = flow {
            emit(Loading)

            try {
                val result = sourceCall()
                emit(Success(result))
            } catch (throwable: Throwable) {
                Timber.e(throwable)
                val message = throwable.message.orEmpty()
                emit(Failure(message))
            }
        }
    }
}

data class Success<out T>(val data: T) : Resource<T>
object Loading : Resource<Nothing>
data class Failure(val message: String) : Resource<Nothing>

inline fun <T, R> Resource<T>.withSuccess(withSuccess: Success<T>.() -> R): Resource<R> {
    return when (this) {
        is Failure -> Failure(message)
        Loading -> Loading
        is Success -> {
            val newData = withSuccess()
            Success(newData)
        }
    }
}

inline fun <T> Resource<T>.withFailure(withFailure: Failure.() -> Unit): Resource<T> {
    return when (this) {
        is Failure -> {
            withFailure()
            Failure(message)
        }
        Loading -> Loading
        is Success -> Success(data)
    }
}

inline fun <T> Resource<T>.withLoading(withLoading: () -> Unit): Resource<T> {
    return when (this) {
        is Failure -> Failure(message)
        Loading -> {
            withLoading()
            Loading
        }
        is Success -> Success(data)
    }
}
