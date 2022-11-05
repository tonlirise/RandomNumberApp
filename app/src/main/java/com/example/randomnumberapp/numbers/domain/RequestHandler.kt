package com.example.randomnumberapp.numbers.domain

interface RequestHandler {
    suspend fun handle(block: suspend () -> Unit): NumbersResult

    class Base(
        private val repository: NumbersRepository,
        private val errorHandler: ErrorHandler
        ) : RequestHandler {
        override suspend fun handle(block: suspend () -> Unit): NumbersResult {
            return try {
                block.invoke()
                NumbersResult.Success(repository.allNumbers())
            } catch (exception: Exception) {
                NumbersResult.Failure(errorHandler.handle(exception))
            }
        }
    }
}