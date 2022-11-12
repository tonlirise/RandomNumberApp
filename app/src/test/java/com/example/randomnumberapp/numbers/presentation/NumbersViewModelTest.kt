package com.example.randomnumberapp.numbers.presentation

import android.view.View
import com.example.randomnumberapp.numbers.domain.NumberFact
import com.example.randomnumberapp.numbers.domain.NumberFactToNumberUi
import com.example.randomnumberapp.numbers.domain.NumbersInteractor
import com.example.randomnumberapp.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : TestBaseClass() {
    private lateinit var numCommunications: TestNumbersCommunications
    private lateinit var numInteractor: TestNumbersInteractor
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: NumbersViewModel

    @Before
    fun init() {
        numCommunications = TestNumbersCommunications()
        numInteractor = TestNumbersInteractor()
        manageResources = TestManageResources()
        viewModel = NumbersViewModel(
            handleResult = HandleNumbersRequest.Base(
                TestDispatchersList(),
                numCommunications,
                NumberResultMapper(numCommunications, NumberFactToNumberUi())
            ),
            communication = numCommunications,
            interactor = numInteractor,
            manageResources = manageResources
        )
    }

    @Test
    fun `init and re-init test`() = runBlocking {
        numInteractor.changeExpectedResult(NumbersResult.Success())
        viewModel.init(isFirstRun = true)

        assertEquals(2, numCommunications.showedProgressVal.size)
        assertEquals(View.VISIBLE, numCommunications.showedProgressVal[0])
        assertEquals(View.GONE, numCommunications.showedProgressVal[1])

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(true, numCommunications.showedCurrentStateVal[0] is UiState.Success)

        assertEquals(0, numCommunications.showedHistoryListVal.size)
        assertEquals(0, numCommunications.countCallShowHistory)

        //get some data
        numInteractor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(View.VISIBLE, numCommunications.showedProgressVal[2])

        assertEquals(1, numInteractor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, numCommunications.showedProgressVal.size)
        assertEquals(View.GONE, numCommunications.showedProgressVal[3])

        assertEquals(2, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Error("no internet connection"), numCommunications.showedCurrentStateVal[1])
        assertEquals(0, numCommunications.countCallShowHistory)

        viewModel.init(isFirstRun = false)
        assertEquals(4, numCommunications.showedProgressVal.size)
        assertEquals(2, numCommunications.showedCurrentStateVal.size)
        assertEquals(0, numCommunications.countCallShowHistory)
    }

    @Test
    fun `fact about empty number test`() = runBlocking {
        manageResources.message = "entered number is empty"
        viewModel.fetchNumberFact("")

        assertEquals(0, numInteractor.fetchAboutNumberCalledList.size)

        assertEquals(0, numCommunications.showedProgressVal.size)

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(UiState.Error("entered number is empty"), numCommunications.showedCurrentStateVal[0])

        assertEquals(0, numCommunications.countCallShowHistory)
    }

    @Test
    fun `fact about some number test`() = runBlocking {
        numInteractor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))))
        viewModel.fetchNumberFact("45")

        assertEquals(View.VISIBLE, numCommunications.showedProgressVal[0])

        assertEquals(1, numInteractor.fetchAboutNumberCalledList.size)
        assertEquals(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))),
            numInteractor.fetchAboutNumberCalledList[0]
        )

        assertEquals(2, numCommunications.showedProgressVal.size)
        assertEquals(View.GONE, numCommunications.showedProgressVal[1])

        assertEquals(1, numCommunications.showedCurrentStateVal.size)
        assertEquals(true, numCommunications.showedCurrentStateVal[0] is UiState.Success)

        assertEquals(1, numCommunications.countCallShowHistory)
        assertEquals(NumberUi("45", "fact about 45"), numCommunications.showedHistoryListVal[0])
    }

    private class TestManageResources : ManageResources {
        var message = ""
        override fun getString(idRes: Int): String = message
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

    private class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}