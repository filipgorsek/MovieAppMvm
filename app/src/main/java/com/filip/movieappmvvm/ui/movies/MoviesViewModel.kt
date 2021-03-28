package com.filip.movieappmvvm.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.common.EventLiveData
import com.filip.movieappmvvm.common.onError
import com.filip.movieappmvvm.common.onSuccess
import com.filip.movieappmvvm.coroutines.CoroutineContextProviderImpl
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.db.MovieDatabaseHelperImpl
import com.filip.movieappmvvm.interaction.BackendInteractorInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(
    private val backend: BackendInteractorInterface,
    private val coroutine: CoroutineContextProviderImpl,
    private val movieDb: MovieDatabaseHelperImpl
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = coroutine.main + job

    private val job = Job()

    val moviesListState = MutableLiveData<MoviesListState>()
    val moviesList = MutableLiveData<MutableList<MovieModel>>()
    val detailsMovie = MutableLiveData<MovieModel>()
    val databaseLiveData = MutableLiveData<ArrayList<MovieModel>>()
    val errorMessage = MutableLiveData<Int>()

    val proceedToDetails = EventLiveData()
    val openFavorites = EventLiveData()

    var databaseMovies = arrayListOf<MovieModel>()

    fun setScreenState(state: MoviesListState) = moviesListState.postValue(state)

    fun checkDbData() {
        launch(coroutine.io) {
            databaseMovies = movieDb.getMovies() as ArrayList<MovieModel>
            databaseLiveData.postValue(databaseMovies)
            openFavorites.triggerUI()
        }
    }

    fun getMoviesData(isNetworkAvailable: Boolean, movieTitle: String) {
        if (isNetworkAvailable) {
            setScreenState(MoviesListState.Loading)
            launch(coroutine.io) {
                backend.getMovies(movieTitle)
                    .onSuccess {
                        if (it.Search.isNullOrEmpty()) {
                            setScreenState(MoviesListState.NoData)
                        } else {
                            setScreenState(MoviesListState.ShowData)
                            moviesList.postValue(it.Search)
                        }
                    }
                    .onError {
                        setScreenState(MoviesListState.ErrorBackend)
                    }

            }
        } else {
            setScreenState(MoviesListState.NoInternet)
        }
    }

    fun setMovieDetails(movieModel: MovieModel) {
        launch(coroutine.io) {
            backend.getMovieDetails(movieModel.imdbID)
                .onSuccess {
                    if (databaseMovies.size > 0) {
                        for (movie in databaseMovies) {
                            if (movie.title == movieModel.title) {
                                detailsMovie.postValue(movie)
                                proceedToDetails.triggerUI()
                                break
                            } else {
                                detailsMovie.postValue(it)
                                proceedToDetails.triggerUI()
                            }
                        }
                    } else {
                        detailsMovie.postValue(it)
                        proceedToDetails.triggerUI()
                    }
                }
                .onError { errorMessage.postValue(R.string.backend_error) }
        }
    }

    fun addToFavorites(movieModel: MovieModel) {
        launch(coroutine.io) {
            movieDb.storeMovie(movieModel)
            detailsMovie.postValue(movieModel)
            databaseMovies = movieDb.getMovies() as ArrayList<MovieModel>
            databaseLiveData.postValue(databaseMovies)
        }
    }

    fun removeFromFavorites(movieModel: MovieModel) {
        launch(coroutine.io) {
            movieDb.deleteMovie(movieModel)
            detailsMovie.postValue(movieModel)
            databaseMovies = movieDb.getMovies() as ArrayList<MovieModel>
            databaseLiveData.postValue(databaseMovies)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

sealed class MoviesListState {
    object Favorites : MoviesListState()
    object NoFavoritesData : MoviesListState()
    object NoInternet : MoviesListState()
    object NoData : MoviesListState()
    object Loading : MoviesListState()
    object ShowData : MoviesListState()
    object ErrorBackend : MoviesListState()
}