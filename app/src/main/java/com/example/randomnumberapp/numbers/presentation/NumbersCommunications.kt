package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers {
    fun showProgress(show: Int)

    fun showCurrentState(state: UiState)

    fun showHistoryList(list: List<NumberUi>)

    class Base(
        val progressCommunication: ProgressCommunication,
        val currentStateCommunication: CurrentStateCommunication,
        val showHistoryListCommunication: HistoryListCommunication
    ) : NumbersCommunications {
        override fun showProgress(show: Int) = progressCommunication.map(show)

        override fun showCurrentState(state: UiState) = currentStateCommunication.map(state)

        override fun showHistoryList(list: List<NumberUi>) = showHistoryListCommunication.map(list)

        override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
            progressCommunication.observe(lifecycleOwner, observer)
        }

        override fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>) {
            currentStateCommunication.observe(lifecycleOwner, observer)
        }

        override fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            showHistoryListCommunication.observe(lifecycleOwner, observer)
        }
    }
}

interface ObserveNumbers {
    fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Int>)

    fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>)

    fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base : Communication.Post<Int>(), ProgressCommunication
}

interface CurrentStateCommunication : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(), CurrentStateCommunication
}

interface HistoryListCommunication : Communication.Mutable<List<NumberUi>> {
    class Base : Communication.Post<List<NumberUi>>(), HistoryListCommunication
}