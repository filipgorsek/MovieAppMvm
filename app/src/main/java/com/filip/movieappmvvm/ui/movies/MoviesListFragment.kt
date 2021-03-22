package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.common.isNetworkAvailable
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.FragmentMoviesListBinding
import com.filip.movieappmvvm.extensions.*
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {

    private val sharedMoviesViewModel by sharedViewModel<MoviesViewModel>()
    private val adapter by lazy { MovieAdapter { movieClicked(it) } }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesListBinding
        get() = FragmentMoviesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDB()
        initUi()
        initListeners()
        subscribeData()

    }

    private fun initListeners() {
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedMoviesViewModel.getMoviesData(
                        activity.isNetworkAvailable(),
                        it
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //
                return false
            }
        })
    }

    private fun initUi() {
        binding.run {
            movieList.layoutManager = LinearLayoutManager(activity)
            movieList.adapter = adapter
        }
    }

    private fun movieClicked(movie: MovieModel) {
        Log.d("Movie",movie.toString())
        sharedMoviesViewModel.setMovieDetails(movie)
        activity?.showFragment(R.id.container, DetailsMovieFragment(), true)
    }

    private fun subscribeData() {
        sharedMoviesViewModel.moviesListState.subscribe(this, ::handleMovieListScreenState)
        sharedMoviesViewModel.moviesList.subscribe(this, ::handleData)
    }

    private fun handleData(list: MutableList<MovieModel>) {
        adapter.setData(list)
    }

    private fun handleMovieListScreenState(state: MoviesListState) {
        when (state) {
            MoviesListState.NoInternet -> showNoInternet()
            MoviesListState.ShowData -> showData()
            MoviesListState.NoData -> noData()
            MoviesListState.Loading -> loading()
            MoviesListState.Favorites -> showFavorites()
        }
    }

    private fun showFavorites() {
        binding.run {
            progress.gone()
        }
    }

    private fun noData() {
        binding.run {
            progress.gone()
        }

    }

    private fun loading() {
        binding.run {
            progress.visible()
        }
    }

    private fun showData() {
        binding.run {
            progress.gone()
        }
    }

    private fun showNoInternet() {
        binding.run {
            progress.gone()
        }
    }

    private fun checkDB() {
        sharedMoviesViewModel.setScreenState(MoviesListState.NoData)
    }
}