package com.filip.movieappmvvm.common

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

@Suppress("unused")
sealed class Result<out T : Any>

data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val httpError: HttpError) : Result<Nothing>()

class HttpError(val error: Throwable?, val errorCode: Int = 0)

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T : Any> Result<T>.onError(action: (HttpError) -> Unit) {
    if (this is Failure) action(httpError)
}

suspend fun <T : Any> Call<T>.awaitResult(): Result<T> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, error: Throwable) {
                continuation.resume(Failure(HttpError(error, 0)))
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                response?.body()?.run { continuation.resume(Success(this)) }
                response?.errorBody()?.run {
                    continuation.resume(
                        Failure(
                            HttpError(
                                Throwable(response.message()),
                                response.code()
                            )
                        )
                    )
                }
            }
        })
        onCompletion(continuation)
    }
}

fun Call<*>.onCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        if (isCanceled) {
            try {
                cancel()
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }
    }
}