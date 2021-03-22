package com.filip.movieappmvvm.api

import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?type=movie")
    fun getMovies(@Query("apikey")apiKey:String,@Query("s") title: String): Call<MovieResponse>

    @GET("?type=movie")
    fun getMovieByID(@Query("i") imdbID: String?): Call<MovieModel>

}