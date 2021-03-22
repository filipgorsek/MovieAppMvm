package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.FragmentFavoritesBinding
import com.filip.movieappmvvm.extensions.showFragment
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FavoritesFragment() : BaseFragment<FragmentFavoritesBinding>() {

    private val sharedMoviesViewModel by sharedViewModel<MoviesViewModel>()
    private val adapter by lazy { MovieAdapter { movieClicked(it) } }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {

    }

    private fun movieClicked(movie: MovieModel) {
        sharedMoviesViewModel.setMovieDetails(movie)
        activity?.showFragment(R.id.container, DetailsMovieFragment(), true)
    }
}