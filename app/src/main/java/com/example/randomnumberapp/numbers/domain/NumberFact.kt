package com.example.randomnumberapp.numbers.domain

data class NumberFact(
    private val num: String,
    private val fact: String
) {
    interface Mapper<T> {
        fun map(num: String, fact: String): T
    }

    fun <T> map(mapper: Mapper<T>) = mapper.map(num, fact)
}