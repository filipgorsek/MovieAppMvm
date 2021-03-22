package com.filip.movieappmvvm.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineContextProviderImpl : CoroutineContextProvider {

    override val io: CoroutineContext by lazy { Dispatchers.Default }
    override val main: CoroutineContext by lazy { Dispatchers.Main }
}

interface CoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}