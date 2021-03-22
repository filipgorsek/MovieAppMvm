package com.filip.movieappmvvm.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context?.isNetworkAvailable(): Boolean {
    this?.let {
        val connectivityManager = it.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
    return false
}