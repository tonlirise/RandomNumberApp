package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class TestBaseClass {
    protected class TestNumbersCommunications : NumbersCommunications {
        val showedProgressVal = mutableListOf<Int>()
        val showedCurrentStateVal = mutableListOf<UiState>()
        var countCallShowHistory = 0
        val showedHistoryListVal = mutableListOf<NumberUi>()

        override fun showProgress(show: Int) {
            showedProgressVal.add(show)
        }

        override fun showCurrentState(state: UiState) {
            showedCurrentStateVal.add(state)
        }

        override fun showHistoryList(list: List<NumberUi>) {
            countCallShowHistory++
            showedHistoryListVal.addAll(list)
        }

        override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) = Unit

        override fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }
}