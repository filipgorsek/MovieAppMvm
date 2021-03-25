package com.filip.movieappmvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filip.movieappmvvm.common.EMPTY
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie")
data class MovieModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "Title")
    @SerializedName("Title")
    val title: String = EMPTY,

    @ColumnInfo(name = "Year")
    @SerializedName("Year")
    val year: String = EMPTY,

    @ColumnInfo(name = "Runtime")
    @SerializedName("Runtime")
    val runtime: String = EMPTY,

    @ColumnInfo(name = "imdbRating")
    @SerializedName("imdbRating")
    val imdbRating: String = EMPTY,

    @ColumnInfo(name = "Genre")
    @SerializedName("Genre")
    val genre: String = EMPTY,

    @ColumnInfo(name = "Plot")
    @SerializedName("Plot")
    val plot: String = EMPTY,

    @ColumnInfo(name = "imdbID")
    @SerializedName("imdbID")
    val imdbID: String = EMPTY,

    @ColumnInfo(name = "Poster")
    @SerializedName("Poster")
    val poster: String = EMPTY,

    @ColumnInfo(name = "Released")
    @SerializedName("Released")
    val released: String = EMPTY,

    @ColumnInfo(name = "isSaved")
    var isSaved: Boolean = false

) : Serializable