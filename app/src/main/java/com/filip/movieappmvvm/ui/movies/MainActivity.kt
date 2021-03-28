package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.databinding.ActivityMainBinding
import com.filip.movieappmvvm.extensions.gone
import com.filip.movieappmvvm.extensions.subscribe
import com.filip.movieappmvvm.extensions.visible
import com.filip.movieappmvvm.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val moviesViewModel by viewModel<MoviesViewModel>()
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.progress.visible()
        moviesViewModel.checkDbData()
        subscribeData()
    }

    private fun subscribeData() {
        moviesViewModel.openFavorites.subscribe(this, ::showFragment)
    }

    private fun showFragment(unit: Unit) {
        binding.progress.gone()
        moviesViewModel.openFavorites.postValue(null)
        replaceFragment(R.id.container, MoviesListFragment(), true)
    }

}