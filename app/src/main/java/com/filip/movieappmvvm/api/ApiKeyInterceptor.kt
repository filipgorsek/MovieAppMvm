package com.filip.movieappmvvm.api

import com.filip.movieappmvvm.common.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var originalHttpUrl: HttpUrl = request.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apike‚‚‚‚←←y", API_KEY)
            .build()

//        request = request.newBuilder().url(url)
        return chain.proceed(request)
    }
}