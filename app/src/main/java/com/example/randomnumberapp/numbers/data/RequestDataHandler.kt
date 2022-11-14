package com.example.randomnumberapp.numbers.data

import com.example.randomnumberapp.numbers.data.cache.NumbersCacheDataSource
import com.example.randomnumberapp.numbers.domain.DomainException
import com.example.randomnumberapp.numbers.domain.ErrorHandler
import com.example.randomnumberapp.numbers.domain.NumberFact

interface RequestDataHandler {
    suspend fun handler(block: suspend () -> NumberData): NumberFact

    class Base(
        private val errorHandler: ErrorHandler<DomainException>,
        private val mapper: NumberData.Mapper<NumberFact>,
        private val cacheDataSource: NumbersCacheDataSource
    ) : RequestDataHandler {
        override suspend fun handler(block: suspend () -> NumberData): NumberFact {
            return try {
                val result = block.invoke()
                cacheDataSource.saveNumber(result)
                result.map(mapper)
            } catch (exception: Exception) {
                throw errorHandler.handle(exception)
            }
        }
    }
}