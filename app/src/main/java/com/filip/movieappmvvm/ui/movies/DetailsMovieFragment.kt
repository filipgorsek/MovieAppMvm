package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.FragmentDetailsMovieBinding
import com.filip.movieappmvvm.extensions.loadDrawable
import com.filip.movieappmvvm.extensions.loadImage
import com.filip.movieappmvvm.extensions.subscribe
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsMovieFragment : BaseFragment<FragmentDetailsMovieBinding>() {

    private val sharedMoviesViewModel by sharedViewModel<MoviesViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsMovieBinding
        get() = FragmentDetailsMovieBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun subscribeData() {
        sharedMoviesViewModel.detailsMovie.subscribe(this, ::showData)

    }

    private fun showData(movie: MovieModel) {
        binding.run {
            with(movie){
                appBarMovieTitle.text = title
                movieImage.loadImage(poster)
                movieTitle.text = title
                tvReleaseDate.text = released
                tvRuntime.text = runtime
                ivImdb.loadDrawable(R.drawable.logo_imdb)
                tvImdbRating.text  = imdbRating
                tvPlot.text = plot
                Log.d("movie", movie.toString())
            }
        }
    }
}