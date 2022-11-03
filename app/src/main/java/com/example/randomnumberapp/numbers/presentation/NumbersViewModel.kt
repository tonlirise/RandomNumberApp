package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomnumberapp.numbers.domain.NumbersInteractor
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val communication: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val numberResultMapper: NumberResultMapper
) : ViewModel(), ObserveNumbers, FetchNumbers {
    override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) {
        communication.observeProgress(lifecycleOwner, observer)
    }

    override fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observeCurrentState(lifecycleOwner, observer)
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communication.showProgress(show = true)
            viewModelScope.launch {
                val result = interactor.init()
                communication.showProgress(show = false)
                result.map(numberResultMapper)
            }
        }
    }

    override fun fetchRandomNumberFact() {
        TODO("Not yet implemented")
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
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