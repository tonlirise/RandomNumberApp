package com.example.randomnumberapp.numbers.domain

import com.example.randomnumberapp.numbers.presentation.NumberUi

class NumberFactToNumberUi : NumberFact.Mapper<NumberUi> {
    override fun map(num: String, fact: String) = NumberUi(num, fact)
}