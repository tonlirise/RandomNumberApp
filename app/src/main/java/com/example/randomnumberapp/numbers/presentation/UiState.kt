package com.example.randomnumberapp.numbers.presentation

sealed class UiState {

    interface Mapper<T> {
        fun map(error: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    class Success() : UiState() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map("")
    }

    data class Error(private val error: String) : UiState() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(error)
    }
}
