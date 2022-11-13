package com.example.randomnumberapp.numbers.presentation

import com.example.randomnumberapp.numbers.domain.NumberFact
import com.example.randomnumberapp.numbers.domain.NumberFactToNumberUi
import org.junit.Assert.*
import org.junit.Test

class NumberResultMapperTest : TestBaseClass() {

    @Test
    fun test_error() {
        val errorMessage = "test error message"
        val communication = TestNumbersCommunications()
        val numberResultMapper = NumberResultMapper(communication, NumberFactToNumberUi())
        numberResultMapper.map(emptyList(), errorMessage)

        assertEquals(UiState.ShowError(errorMessage), communication.showedCurrentStateVal[0])
    }

    @Test
    fun test_empty_list() {
        val communication = TestNumbersCommunications()
        val numberResultMapper = NumberResultMapper(communication, NumberFactToNumberUi())
        numberResultMapper.map(emptyList(), "")

        assertEquals(0, communication.countCallShowHistory)
        assertEquals(true, communication.showedCurrentStateVal[0] is UiState.Success)
    }

    @Test
    fun test_not_empty_list() {
        val communication = TestNumbersCommunications()
        val numberResultMapper = NumberResultMapper(communication, NumberFactToNumberUi())
        numberResultMapper.map(listOf(NumberFact("5", "test information 5")), "")

        assertEquals(1, communication.countCallShowHistory)
        assertEquals(true, communication.showedCurrentStateVal[0] is UiState.Success)
        assertEquals(NumberUi("5", "test information 5"), communication.showedHistoryListVal[0])
    }
}