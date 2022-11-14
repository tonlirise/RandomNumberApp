package com.example.randomnumberapp.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.randomnumberapp.BuildConfig

class NumbersApp : Application(), ProvideViewModel {

    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        viewModelFactory = ViewModelFactory(DependencyContainer.Base(Core.Base(this, /*BuildConfig.DEBUG*/true)))
    }

    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, viewModelFactory)[clasz]
    }
}