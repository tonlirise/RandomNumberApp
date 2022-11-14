package com.example.randomnumberapp.main.sl

import android.content.Context
import com.example.randomnumberapp.numbers.data.cache.CacheModule
import com.example.randomnumberapp.numbers.data.cloud.CloudModule
import com.example.randomnumberapp.numbers.presentation.DispatchersList
import com.example.randomnumberapp.numbers.presentation.ManageResources

interface Core : ManageResources, CloudModule, CacheModule {

    fun provideDispatchers(): DispatchersList

    class Base(context: Context, val isRelease: Boolean) : Core {
        private val manageResources: ManageResources = ManageResources.Base(context)

        override fun getString(idRes: Int) = manageResources.getString(idRes)

        private val dispatchersList by lazy {
            DispatchersList.Base()
        }

        override fun provideDispatchers() = dispatchersList

        private val cloudModule by lazy {
            if (isRelease)
                CloudModule.Release()
            else
                CloudModule.Debug()
        }

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)

        private val cacheModule by lazy {
            if (isRelease)
                CacheModule.Base(context)
            else
                CacheModule.Mock(context)
        }

        override fun provideDataBase() = cacheModule.provideDataBase()
    }
}