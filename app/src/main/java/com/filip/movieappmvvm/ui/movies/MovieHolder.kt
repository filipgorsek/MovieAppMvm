package com.filip.movieappmvvm.ui.movies

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.ItemSearchedMovieBinding
import com.filip.movieappmvvm.extensions.loadImage
import com.filip.movieappmvvm.extensions.onClick

class MovieHolder(private val binding: ItemSearchedMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(movie: MovieModel, movieClicked: (MovieModel) -> Unit) {
        binding.run {
            with(movie) {
                root.onClick { movieClicked(movie) }
                if (poster.isNotEmpty()) {
                    movieImage.loadImage(poster)
                }
                yearOfMovie.text = year
                movieTitle.text = title
                movieFavoriteState.isChecked = movie.isSaved
                movieFavoriteState.onClick {  }
            }
        }
    }
}