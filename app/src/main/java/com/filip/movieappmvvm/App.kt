package com.filip.movieappmvvm

import android.app.Application
import android.content.Context
import com.filip.movieappmvvm.db.MovieDatabase
import com.filip.movieappmvvm.db.MovieDatabaseHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.BuildConfig
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(com.filip.movieappmvvm.di.modules)
        }
    }

}