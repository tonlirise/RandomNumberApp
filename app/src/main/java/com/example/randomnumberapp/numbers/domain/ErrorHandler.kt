package com.example.randomnumberapp.numbers.domain

import com.example.randomnumberapp.R
import com.example.randomnumberapp.numbers.presentation.ManageResources

interface ErrorHandler<T> {
    fun handle(exception: Exception): T

    class Base(private val manageResources: ManageResources) : ErrorHandler<String> {
        override fun handle(exception: Exception) = manageResources.getString(
            when (exception) {
                is NoInternetConnectionException -> R.string.no_internet_connection_exception
                else -> R.string.service_is_unavailable_exception
            }
        )
    }
}