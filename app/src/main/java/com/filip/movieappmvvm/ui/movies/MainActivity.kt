package com.filip.movieappmvvm.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.databinding.ActivityMainBinding
import com.filip.movieappmvvm.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val moviesViewModel by viewModel<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private fun initUi() {
//        if (moviesViewModel.hasFavorites()) {
//            replaceFragment(R.id.container, FavoritesFragment(), true)
//        } else {
            replaceFragment(R.id.container, MoviesListFragment(), true)
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        }
    }
}