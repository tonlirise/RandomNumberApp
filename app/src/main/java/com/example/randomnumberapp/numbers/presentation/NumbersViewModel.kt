package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnumberapp.R
import com.example.randomnumberapp.numbers.domain.NumbersInteractor
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val communication: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val manageResources: ManageResources
) : ViewModel(), ObserveNumbers, FetchNumbers {
    override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        communication.observeProgress(lifecycleOwner, observer)
    }

    override fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observeCurrentState(lifecycleOwner, observer)
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            handleResult.handle(viewModelScope) {
                interactor.init()
            }
        }
    }

    override fun fetchRandomNumberFact() = handleResult.handle(viewModelScope) {
        interactor.factAboutRandomNumber()
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communication.showCurrentState(UiState.Error(manageResources.getString(R.string.entered_number_is_empty)))
        else
            handleResult.handle(viewModelScope) {
                interactor.factAboutNumber(number)
            }
    }

    override fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
        communication.observeHistoryList(lifecycleOwner, observer)
    }
}

interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}