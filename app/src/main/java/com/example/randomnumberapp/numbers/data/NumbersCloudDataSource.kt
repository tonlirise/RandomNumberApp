package com.example.randomnumberapp.numbers.data

interface NumbersCloudDataSource : FetchNumber {
    suspend fun randomNumber(): NumberData
}
