package com.example.randomnumberapp.numbers.data

interface FetchNumber {
    suspend fun number(number: String): NumberData
}