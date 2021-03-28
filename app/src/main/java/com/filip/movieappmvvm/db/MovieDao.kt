package com.filip.movieappmvvm.db

import androidx.room.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.filip.movieappmvvm.data.model.MovieModel


@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieModel>

    @Insert
    suspend fun storeMovie(movie: MovieModel)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

}