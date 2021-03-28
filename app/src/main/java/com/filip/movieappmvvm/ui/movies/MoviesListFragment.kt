package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
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
import com.filip.movieappmvvm.extensions.gone
import com.filip.movieappmvvm.extensions.showFragment
import com.filip.movieappmvvm.extensions.subscribe
import com.filip.movieappmvvm.extensions.visible
import com.filip.movieappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {

    private val sharedMoviesViewModel by sharedViewModel<MoviesViewModel>()
    private val movieAdapter by lazy { MovieAdapter { movieClicked(it) } }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesListBinding
        get() = FragmentMoviesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        subscribeData()
    }

    private fun initUi() {
        binding.run {
            movieList.layoutManager = LinearLayoutManager(activity)
            movieList.adapter = movieAdapter
        }
    }

    private fun initListeners() {
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedMoviesViewModel.getMoviesData(activity.isNetworkAvailable(), it)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean { return false }
        })
    }

    private fun subscribeData() {
        sharedMoviesViewModel.moviesListState.subscribe(this, ::handleMovieListScreenState)
        sharedMoviesViewModel.moviesList.subscribe(this, ::handleData)
        sharedMoviesViewModel.proceedToDetails.subscribe(this, ::proceedToDetails)
        sharedMoviesViewModel.databaseLiveData.subscribe(this, ::showFavorites)
        sharedMoviesViewModel.errorMessage.subscribe(this, ::showError)
    }

    private fun movieClicked(movie: MovieModel) {
        sharedMoviesViewModel.setMovieDetails(movie)
    }
    private fun showError(errorMessage: Int) {
        Toast.makeText(activity, getString(errorMessage), Toast.LENGTH_SHORT).show()
    }

    private fun showFavorites(arrayList: ArrayList<MovieModel>) {
        if (!arrayList.isNullOrEmpty()) {
            arrayList.sortWith(Comparator { movieLower, movieHigher ->
                Integer.valueOf(movieLower.year).compareTo(Integer.valueOf(movieHigher.year))
            })
            binding.tvNoFavorites.gone()
            movieAdapter.setData(arrayList)
        } else {
            movieAdapter.setData(arrayList)
            sharedMoviesViewModel.setScreenState(MoviesListState.NoFavoritesData)
        }
    }

    private fun proceedToDetails(unit: Unit?) {
        sharedMoviesViewModel.proceedToDetails.postValue(null)
        activity?.showFragment(R.id.container, DetailsMovieFragment(), true)
    }

    private fun handleData(list: MutableList<MovieModel>) {
        list.sortWith(Comparator { movieLower, movieHigher ->
            Integer.valueOf(movieLower.year).compareTo(Integer.valueOf(movieHigher.year))
        })
        movieAdapter.setData(list)
    }

    private fun handleMovieListScreenState(state: MoviesListState) {
        when (state) {
            MoviesListState.NoInternet -> showNoInternet()
            MoviesListState.NoFavoritesData -> showNoFavorites()
            MoviesListState.ShowData -> showData()
            MoviesListState.NoData -> noData()
            MoviesListState.Loading -> loading()
            MoviesListState.Favorites -> showFavorites()
        }
    }

    private fun showNoFavorites() {
        binding.run {
            progress.gone()
            tvNoFavorites.visible()
        }
    }

    private fun showFavorites() {
        binding.run {
            progress.gone()
            tvNoFavorites.gone()
        }
    }

    private fun noData() {
        binding.run {
            progress.gone()
            Toast.makeText(activity, getString(R.string.no_movie), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loading() {
        binding.run {
            progress.visible()
            tvNoFavorites.gone()
        }
    }

    private fun showData() {
        binding.run {
            progress.gone()
            tvNoFavorites.gone()
        }
    }

    private fun showNoInternet() {
        binding.run {
            progress.gone()
        }
    }
}