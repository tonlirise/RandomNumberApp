package com.example.randomnumberapp.numbers.presentation

import com.example.randomnumberapp.numbers.domain.NumberFact
import com.example.randomnumberapp.numbers.domain.NumbersResult

class NumberResultMapper(
    private val communication: NumbersCommunications,
    private val numberFactToNumberUi: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {
    override fun map(list: List<NumberFact>, error: String) {
        communication.showCurrentState(
            if (error.isEmpty()) {
                val numList = list.map { it.map(numberFactToNumberUi) }
                if (numList.isNotEmpty()) communication.showHistoryList(numList)
                UiState.Success()
            } else {
                UiState.Error(error)
            }
        )
    }

}