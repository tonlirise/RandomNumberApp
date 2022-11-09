package com.example.randomnumberapp.numbers.data

interface NumbersCacheDataSource: FetchNumber {
    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)
}

