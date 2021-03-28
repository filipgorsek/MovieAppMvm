package com.filip.movieappmvvm.di

import android.content.Context
import androidx.room.Room
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.api.ApiKeyInterceptor
import com.filip.movieappmvvm.api.ApiService
import com.filip.movieappmvvm.coroutines.CoroutineContextProviderImpl
import com.filip.movieappmvvm.db.MovieDatabase
import com.filip.movieappmvvm.db.MovieDatabaseHelper
import com.filip.movieappmvvm.db.MovieDatabaseHelperImpl
import com.filip.movieappmvvm.interaction.BackendInteractor
import com.filip.movieappmvvm.interaction.BackendInteractorInterface
import com.filip.movieappmvvm.ui.movies.MoviesViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PREFERENCES_NAME = "movieAppPrefs"
private const val LOGGING_INTERCEPTOR = "logging"
private const val BACKEND_RETROFIT_NEWS_APP = "movieAppBackend"

val appModule = module {
    single { androidContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }
}

val dbModule = module{
    single { Room.databaseBuilder(get(), MovieDatabase::class.java, "movies_database").build() }
    single { get<MovieDatabase>().movieDao() }
    single { MovieDatabaseHelperImpl(get()) }

}

val networkingModule = module {
    single { GsonConverterFactory.create() as Converter.Factory }

    single(named(LOGGING_INTERCEPTOR)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //okHttpClient
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>(named(LOGGING_INTERCEPTOR)))
            }
            readTimeout(1L, TimeUnit.MINUTES)
            connectTimeout(1L, TimeUnit.MINUTES)
        }.build()
    }
    single(named(BACKEND_RETROFIT_NEWS_APP)) {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.base_backend_url))
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single { get<Retrofit>(named(BACKEND_RETROFIT_NEWS_APP)).create(ApiService::class.java) }
}

val interactionModule = module {
    single<BackendInteractorInterface> { BackendInteractor(get()) }
}

val viewModule = module {
    viewModel { MoviesViewModel(get(), get(),get()) }
}

val coroutineModule = module {
    single { CoroutineContextProviderImpl() }
}

val modules = listOf(appModule, dbModule, networkingModule, interactionModule, viewModule, coroutineModule)