package com.example.randomnumberapp.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers {
    fun showProgress(show: Boolean)

    fun showCurrentState(state: UiState)

    fun showHistoryList(list: List<NumberUi>)

    class Base(
        val progressCommunication: ProgressCommunication,
        val currentStateCommunication: CurrentStateCommunication,
        val showHistoryListCommunication: HistoryListCommunication
    ) : NumbersCommunications {
        override fun showProgress(show: Boolean) = progressCommunication.map(show)

        override fun showCurrentState(state: UiState) = currentStateCommunication.map(state)

        override fun showHistoryList(list: List<NumberUi>) = showHistoryListCommunication.map(list)

        override fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) {
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
    fun observeProgress(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>)

    fun observeCurrentState(lifecycleOwner: LifecycleOwner, observer: Observer<UiState>)

    fun observeHistoryList(lifecycleOwner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication : Communication.Mutable<Boolean> {
    class Base : Communication.Post<Boolean>(), ProgressCommunication
}

interface CurrentStateCommunication : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(), CurrentStateCommunication
}

interface HistoryListCommunication : Communication.Mutable<List<NumberUi>> {
    class Base : Communication.Post<List<NumberUi>>(), HistoryListCommunication
}