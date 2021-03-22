package com.filip.movieappmvvm.data.model

import com.filip.movieappmvvm.common.EMPTY
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieModel(

    val id: Long = 0L,

    @SerializedName("Title")
    val title: String = EMPTY,

    @SerializedName("Year")
    val year: String = EMPTY,

    @SerializedName("Runtime")
    val runtime: String = EMPTY,

    @SerializedName("imdbRating")
    val imdbRating: String = EMPTY,

    @SerializedName("Genre")
    val genre: String = EMPTY,

    @SerializedName("Plot")
    val plot: String = EMPTY,

    @SerializedName("imdbID")
    val imdbID: String = EMPTY,

    @SerializedName("Poster")
    val poster: String = EMPTY,

    @SerializedName("Released")
    val released: String = EMPTY,

    val isSaved: Boolean = false

) : Serializable