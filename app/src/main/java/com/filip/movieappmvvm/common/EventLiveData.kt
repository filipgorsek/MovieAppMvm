package com.filip.movieappmvvm.common

import androidx.lifecycle.MutableLiveData

class EventLiveData : MutableLiveData<Unit>() {

    fun triggerUI() {
        postValue(Unit)
    }

    operator fun invoke() {
        value = Unit
    }
}