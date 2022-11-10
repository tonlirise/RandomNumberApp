package com.example.randomnumberapp.main

import android.app.Application
import com.example.randomnumberapp.numbers.data.NumberService
import com.example.randomnumberapp.numbers.data.NumbersCloudDataSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class NumbersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //todo refactor
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    }
}