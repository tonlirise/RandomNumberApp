package com.example.randomnumberapp.numbers.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumberService {

    @GET("{id}")
    suspend fun number(@Path("id") id: String): String

    @GET("random/math")
    suspend fun randomNumber(): Response<String>
}