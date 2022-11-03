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
                if (list.isNotEmpty()) {
                    communication.showHistoryList(list.map { it.map(numberFactToNumberUi) })
                }
                UiState.Success()
            } else {
                UiState.Error(error)
            }
        )
    }

}