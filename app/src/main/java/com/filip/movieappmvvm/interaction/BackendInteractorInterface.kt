package com.filip.movieappmvvm.interaction

import com.filip.movieappmvvm.common.Result
import com.filip.movieappmvvm.data.response.MovieResponse

interface BackendInteractorInterface {

    suspend fun getMovies(title: String): Result<MovieResponse>

}