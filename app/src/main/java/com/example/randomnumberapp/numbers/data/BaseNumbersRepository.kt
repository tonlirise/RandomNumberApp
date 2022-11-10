package com.example.randomnumberapp.numbers.data

import com.example.randomnumberapp.numbers.domain.NumberFact
import com.example.randomnumberapp.numbers.domain.NumbersRepository

class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource,
    private val mapper: MapperNumberDataToDomain,
    private val requestHandler: RequestHandler
) : NumbersRepository {
    override suspend fun allNumbers() = cacheDataSource.allNumbers().map { it.map(mapper) }

    override suspend fun numberFact(number: String): NumberFact {
        return  requestHandler.handler {
            val source = if (cacheDataSource.contains(number)) cacheDataSource else cloudDataSource
            source.number(number)
        }
    }

    override suspend fun randomNumberFact() = requestHandler.handler { cloudDataSource.randomNumber() }
}

