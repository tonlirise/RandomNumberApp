package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication {

    interface Observe<T> {
        fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
    }

    interface Mutate<T> : Mapper.Unit<T>

    interface Mutable<T> : Observe<T>, Mutate<T>

    abstract class Abstract<T>(protected val liveData: MutableLiveData<T> = MutableLiveData()) : Mutable<T> {
        override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(lifecycleOwner, observer)
        }
    }

    abstract class Ui<T>(liveData: MutableLiveData<T> = MutableLiveData<T>()) : Abstract<T>(liveData) {
        override fun map(source: T) {
            liveData.value = source
        }
    }

    abstract class Post<T>(liveData: MutableLiveData<T> = MutableLiveData<T>()) : Abstract<T>(liveData) {
        override fun map(source: T) {
            liveData.postValue(source)
        }
    }
}