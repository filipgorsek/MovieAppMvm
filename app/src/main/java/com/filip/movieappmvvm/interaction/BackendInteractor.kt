package com.filip.movieappmvvm.interaction

import com.filip.movieappmvvm.api.ApiService
import com.filip.movieappmvvm.common.*
import com.filip.movieappmvvm.data.response.MovieResponse
import org.koin.core.KoinComponent

const val ERROR_CODE = 404

class BackendInteractor(private val apiService: ApiService) : BackendInteractorInterface,
    KoinComponent {

    override suspend fun getMovies(
        title: String
    ): Result<MovieResponse> {
        apiService.getMovies(API_KEY,title)
            .awaitResult()
            .onSuccess { return Success(it) }
            .onError { return handleError(it) }
        return Failure(HttpError(Throwable(), ERROR_CODE))
    }

    private fun handleError(error: HttpError): Failure {
        return Failure(error)
    }
}