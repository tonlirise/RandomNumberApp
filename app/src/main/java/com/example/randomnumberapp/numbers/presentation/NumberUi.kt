package com.example.randomnumberapp.numbers.presentation

import android.widget.TextView

data class NumberUi(
    private val num: String,
    private val fact: String
) : Mapper<NumberUi, Boolean> {
    fun map(head: TextView, main: TextView) {
        head.text = num
        main.text = fact
    }

    fun ui() = "$num\n\n$fact"

    override fun map(source: NumberUi): Boolean {
        return source.num == num
    }
}
