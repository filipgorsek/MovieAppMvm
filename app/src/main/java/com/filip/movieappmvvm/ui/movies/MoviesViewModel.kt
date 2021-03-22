package com.filip.movieappmvvm.ui.movies

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filip.movieappmvvm.common.onError
import com.filip.movieappmvvm.common.onSuccess
import com.filip.movieappmvvm.coroutines.CoroutineContextProviderImpl
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.interaction.BackendInteractorInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(
    private val backend: BackendInteractorInterface,
    private val coroutine: CoroutineContextProviderImpl,

    ) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = coroutine.main + job

    private val job = Job()

    val moviesListState = MutableLiveData<MoviesListState>()
    val moviesList = MutableLiveData<MutableList<MovieModel>>()
    val detailsMovie = MutableLiveData<MovieModel>()

    fun setScreenState(state: MoviesListState) {
        moviesListState.postValue(state)
    }

    fun setMovieDetails(movieModel: MovieModel) {
        detailsMovie.postValue(movieModel)
    }

    fun getMoviesData(isNetworkAvailable: Boolean, movieTitle: String) {
        if (isNetworkAvailable) {
            setScreenState(MoviesListState.Loading)
            launch(coroutine.io) {
                backend.getMovies(movieTitle)
                    .onSuccess {
                        if (it.Search.isEmpty()){
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


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

sealed class MoviesListState {
    object Favorites : MoviesListState()
    object NoInternet : MoviesListState()
    object NoData : MoviesListState()
    object Loading : MoviesListState()
    object ShowData : MoviesListState()
    object ErrorBackend : MoviesListState()
    object ErrorNotFound : MoviesListState()
    object ErrorUnauthorized : MoviesListState()
    object ErrorBadRequest : MoviesListState()
    object ErrorInternalServer : MoviesListState()
}