package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.randomnumberapp.numbers.domain.NumberFact
import com.example.randomnumberapp.numbers.domain.NumberFactToNumberUi
import com.example.randomnumberapp.numbers.domain.NumbersInteractor
import com.example.randomnumberapp.numbers.domain.NumbersResult
import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {

    @Test
    fun `init and re-init test`() {
        val numCommunications = TestNumbersCommunications()
        val numInteractor = TestNumbersInteractor()

        //initialize case
        val viewModel = NumbersViewModel(numCommunications, numInteractor, NumberResultMapper(numCommunications, NumberFactToNumberUi()))
        numInteractor.changeExpectedResult(NumbersResult.Success())
        //2. action
        viewModel.init(isFirstRun = true)
        //3. check
        assertEquals(2, numCommunications.showedProgressVal.size)
        assertEquals(true, numCommunications.showedProgressVal[0])
        assertEquals(false, numCommunications.showedProgressVal[1])

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Success(), numCommunications.showedCurrentStateVal[0])

        assertEquals(0, numCommunications.showedHistoryListVal.size)
        assertEquals(0, numCommunications.countCallShowHistory)

        //get some data
        numInteractor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(true, numCommunications.showedProgressVal[2])

        assertEquals(1, numInteractor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, numCommunications.showedProgressVal.size)
        assertEquals(false, numCommunications.showedProgressVal[3])

        assertEquals(2, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Error("no internet connection"), numCommunications.showedCurrentStateVal[1])
        assertEquals(0, numCommunications.countCallShowHistory)

        viewModel.init(isFirstRun = false)
        assertEquals(4, numCommunications.showedProgressVal.size)
        assertEquals(2, numCommunications.showedCurrentStateVal.size)
        assertEquals(0, numCommunications.countCallShowHistory)
    }

    @Test
    fun `fact about empty number test`() {
        val numCommunications = TestNumbersCommunications()
        val numInteractor = TestNumbersInteractor()

        val viewModel = NumbersViewModel(numCommunications, numInteractor, NumberResultMapper(numCommunications, NumberFactToNumberUi()))

        viewModel.fetchNumberFact("")

        assertEquals(0, numInteractor.fetchAboutNumberCalledList.size)

        assertEquals(0, numCommunications.showedProgressVal.size)

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Error("entered number is empty"), numCommunications.showedCurrentStateVal[0])

        assertEquals(0, numCommunications.countCallShowHistory)
    }

    @Test
    fun `fact about some number test`() {
        val numCommunications = TestNumbersCommunications()
        val numInteractor = TestNumbersInteractor()

        val viewModel = NumbersViewModel(numCommunications, numInteractor, NumberResultMapper(numCommunications, NumberFactToNumberUi()))

        numInteractor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))))
        viewModel.fetchNumberFact("45")

        assertEquals(true, numCommunications.showedProgressVal[0])

        assertEquals(1, numInteractor.fetchAboutNumberCalledList.size)
        assertEquals(NumberFact("45", "fact about 45"), numInteractor.fetchAboutNumberCalledList[0])

        assertEquals(2, numCommunications.showedProgressVal.size)
        assertEquals(false, numCommunications.showedProgressVal[1])

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Success(), numCommunications.showedCurrentStateVal[0])

        assertEquals(1, numCommunications.countCallShowHistory)
        assertEquals(NumberUi("45", " fact about 45"), numCommunications.showedHistoryListVal[0])
    }

    private class TestNumbersCommunications : NumbersCommunications {
        val showedProgressVal = mutableListOf<Boolean>()
        val showedCurrentStateVal = mutableListOf<UiState>()
        var countCallShowHistory = 0
        val showedHistoryListVal = mutableListOf<NumberUi>()

        override fun showProgress(show: Boolean) {
            showedProgressVal.add(show)
        }

        override fun showCurrentState(state: UiState) {
            showedCurrentStateVal.add(state)
        }

        override fun showHistoryList(list: List<NumberUi>) {
            countCallShowHistory++
            showedHistoryListVal.addAll(list)
        }

        override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) = Unit

        override fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }

    private class TestNumbersInteractor : NumbersInteractor {
        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}