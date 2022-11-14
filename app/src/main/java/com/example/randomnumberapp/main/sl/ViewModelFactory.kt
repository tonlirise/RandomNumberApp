package com.example.randomnumberapp.main.sl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val dependencyConteiner: DependencyContainer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return dependencyConteiner.module(modelClass).viewModel() as T
    }
}