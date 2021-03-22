package com.filip.movieappmvvm.data.response

import com.filip.movieappmvvm.data.model.MovieModel
import java.io.Serializable

class MovieResponse(var Search: MutableList<MovieModel> = mutableListOf()) : Serializable