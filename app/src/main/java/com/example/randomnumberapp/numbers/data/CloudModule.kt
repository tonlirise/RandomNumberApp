package com.example.randomnumberapp.numbers.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {
    fun <T> service(clasz: Class<T>): T

    abstract class Abstract : CloudModule {
        protected abstract val baseUrl: String
        protected abstract val logLevel: HttpLoggingInterceptor.Level

        override fun <T> service(clasz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(logLevel)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(clasz)
        }
    }

    class Release : Abstract(){
        override val baseUrl: String
            get() = "http://numbersapi.com/"
        override val logLevel: HttpLoggingInterceptor.Level
            get() = HttpLoggingInterceptor.Level.BODY
    }

    class Debug : Abstract(){
        override val baseUrl: String
            get() = "http://test.numbersapi.com/"
        override val logLevel: HttpLoggingInterceptor.Level
            get() = HttpLoggingInterceptor.Level.NONE
    }
}