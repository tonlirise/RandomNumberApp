package com.example.randomnumberapp.numbers.data

import com.example.randomnumberapp.numbers.data.cache.NumbersCacheDataSource
import com.example.randomnumberapp.numbers.domain.NumberFact

interface RequestHandler {
    suspend fun handler(block: suspend () -> NumberData): NumberFact

    class Base(
        private val errorHandler: DataToDomainErrorHandler,
        private val mapper: MapperNumberDataToDomain,
        private val cacheDataSorce: NumbersCacheDataSource
    ) : RequestHandler {
        override suspend fun handler(block: suspend () -> NumberData): NumberFact {
            return try {
                val result = block.invoke()
                cacheDataSorce.saveNumber(result)
                result.map(mapper)
            } catch (exception: Exception) {
                throw errorHandler.handle(exception)
            }
        }
    }
}