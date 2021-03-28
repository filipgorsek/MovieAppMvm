package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.FragmentDetailsMovieBinding
import com.filip.movieappmvvm.extensions.*
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsMovieFragment : BaseFragment<FragmentDetailsMovieBinding>() {

    private val sharedMoviesViewModel by sharedViewModel<MoviesViewModel>()

    private var movieModel: MovieModel = MovieModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsMovieBinding
        get() = FragmentDetailsMovieBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        subscribeData()
    }

    private fun initListeners() {
        binding.run {
            backBtn.onClick {
                goBack()
                sharedMoviesViewModel.checkDbData()
            }
            movieSaved.onClick {
                movieModel.isSaved = false
                sharedMoviesViewModel.removeFromFavorites(movieModel)
            }
            movieNotSaved.onClick {
                movieModel.isSaved = true
                sharedMoviesViewModel.addToFavorites(movieModel)
            }
        }
    }

    private fun subscribeData() {
        sharedMoviesViewModel.detailsMovie.subscribe(this, ::showData)
    }

    private fun showData(movie: MovieModel) {
        binding.run {
            with(movie) {
                movieModel = this
                appBarMovieTitle.text = title
                movieImage.loadImage(poster)
                movieTitle.text = title
                tvReleaseDate.text = released
                tvRuntime.text = runtime
                ivImdb.loadDrawable(R.drawable.logo_imdb)
                tvImdbRating.text = String.format(getString(R.string.max_rating), imdbRating)
                tvPlot.text = plot
                genreList.text = genre
                if (isSaved) {
                    movieSaved.visible()
                    movieNotSaved.gone()
                } else {
                    movieSaved.gone()
                    movieNotSaved.visible()
                }
            }
        }
    }
}