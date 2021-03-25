package com.filip.movieappmvvm.db

import com.filip.movieappmvvm.data.model.MovieModel
import org.koin.core.KoinComponent

class MovieDatabaseHelperImpl(private val movieDatabase: MovieDatabase) : MovieDatabaseHelper,
    KoinComponent {

    override suspend fun getMovies(): List<MovieModel> = movieDatabase.movieDao().getAll()

    override suspend fun storeMovie(movie: MovieModel) {
        movieDatabase.movieDao().storeMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieModel) {
        movieDatabase.movieDao().deleteMovie(movie)
    }
}