package com.example.randomnumberapp.numbers.presentation

sealed class UiState {

    //todo views on fragment

    class Success : UiState() {

    }

    data class Error(private val message: String) : UiState() {
    }
}
