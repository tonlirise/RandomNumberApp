package com.example.randomnumberapp.numbers.domain

interface NumbersInteractor {
    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult

    class Base(
        private val repository: NumbersRepository,
        private val requestHandler: RequestHandler
    ) : NumbersInteractor {
        override suspend fun init(): NumbersResult = NumbersResult.Success(repository.allNumbers())

        override suspend fun factAboutNumber(number: String) = requestHandler.handle { repository.numberFact(number) }

        override suspend fun factAboutRandomNumber() = requestHandler.handle { repository.randomNumberFact() }
    }
}