package com.filip.movieappmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.filip.movieappmvvm.data.model.MovieModel
import org.koin.core.KoinComponent


@Database(entities = [MovieModel::class], version = 1)
abstract class MovieDatabase : RoomDatabase(), KoinComponent {

    abstract fun movieDao(): UserDao

}