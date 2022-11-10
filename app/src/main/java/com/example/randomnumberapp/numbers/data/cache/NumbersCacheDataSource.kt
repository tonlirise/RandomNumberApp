package com.example.randomnumberapp.numbers.data.cache

import com.example.randomnumberapp.numbers.data.FetchNumber
import com.example.randomnumberapp.numbers.data.NumberData

interface NumbersCacheDataSource: FetchNumber {
    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)
}

