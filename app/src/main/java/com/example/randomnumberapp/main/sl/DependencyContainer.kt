package com.example.randomnumberapp.main.sl

import androidx.lifecycle.ViewModel
import com.example.randomnumberapp.numbers.presentation.NumbersViewModel
import com.example.randomnumberapp.numbers.sl.NumbersModule

interface DependencyContainer {
    fun <T : ViewModel> module(clasz: Class<T>): Module<*>

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun <T : ViewModel> module(clasz: Class<T>): Module<*> {
            return if (clasz == NumbersViewModel::class.java) {
                return NumbersModule(core)
            } else {
                dependencyContainer.module(clasz)
            }
        }
    }

    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clasz: Class<T>): Module<*> {
            throw IllegalStateException("Not found module with name: $clasz")
        }
    }
}