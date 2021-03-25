package com.filip.movieappmvvm.ui.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.FragmentDetailsMovieBinding
import com.filip.movieappmvvm.extensions.*
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.ext.android.bind
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
        binding.run { backBtn.onClick { goBack() } }
        binding.movieFavoriteState.onCheckChange { isFavorite ->
            if (isFavorite){
                movieModel.isSaved = true
                sharedMoviesViewModel.addToFavorites(movieModel)
            }
            else {
                movieModel.isSaved = false
                sharedMoviesViewModel.removeFromFavorites(movieModel)
            }
        }
    }

    private fun subscribeData() {
        sharedMoviesViewModel.detailsMovie.subscribe(this, ::showData)
        sharedMoviesViewModel.showDataMovieFromBackend.subscribe(this,::showMovieData)
        sharedMoviesViewModel.showDataMovieFromDB.subscribe(this,::showMovieData)
    }

    private fun showMovieData(movie: MovieModel) {
        binding.run {
            Log.d("MovieDb",movie.toString())
            with(movie) {
                movieModel = this
                appBarMovieTitle.text = title
                movieImage.loadImage(poster)
                movieTitle.text = title
                tvReleaseDate.text = released
                tvRuntime.text = runtime
                ivImdb.loadDrawable(com.filip.movieappmvvm.R.drawable.logo_imdb)
                tvImdbRating.text = "$imdbRating/10"
                tvPlot.text = plot
                genreList.text = genre
                movieFavoriteState.isChecked = isSaved
            }
        }
    }



    private fun showData(movie: MovieModel) {
        checkDb(movie)
    }

    private fun checkDb(movie: MovieModel) {
        sharedMoviesViewModel.checkIfMovieExist(movie)
    }
}