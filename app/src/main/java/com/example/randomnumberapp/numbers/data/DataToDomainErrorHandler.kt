package com.example.randomnumberapp.numbers.data

import com.example.randomnumberapp.numbers.domain.DomainException
import com.example.randomnumberapp.numbers.domain.ErrorHandler
import com.example.randomnumberapp.numbers.domain.NoInternetConnectionException
import com.example.randomnumberapp.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class DataToDomainErrorHandler : ErrorHandler<DomainException> {

    override fun handle(exception: Exception) = when (exception) {
        is UnknownHostException -> NoInternetConnectionException()
        else -> ServiceUnavailableException()
    }
}