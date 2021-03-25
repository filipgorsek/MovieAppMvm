package com.filip.movieappmvvm.db

import com.filip.movieappmvvm.data.model.MovieModel

interface MovieDatabaseHelper {

    suspend fun getMovies(): List<MovieModel>

    suspend fun storeMovie(movie:MovieModel)

    suspend fun deleteMovie(movie: MovieModel)
}