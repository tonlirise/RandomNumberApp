package com.example.randomnumberapp.numbers.data

data class NumberData(val id: String, val fact: String) {
    interface Mapper<T> {
        fun map(id: String, fact: String): T

        class Matches(private val nId: String) : Mapper<Boolean>{
            override fun map(id: String, fact: String) = nId == id
        }
    }

    fun <T> map(mapper: Mapper<T>) = mapper.map(id, fact)

}