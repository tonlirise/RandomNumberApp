package com.example.randomnumberapp.numbers.presentation

import android.widget.TextView

data class NumberUi(
    private val num: String,
    private val fact: String
) {
    fun map(head: TextView, main: TextView) {
        head.text = num
        main.text = fact
    }
}
