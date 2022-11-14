package com.example.randomnumberapp.numbers.data.cache

import com.example.randomnumberapp.numbers.data.FetchNumber
import com.example.randomnumberapp.numbers.data.NumberData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface NumbersCacheDataSource : FetchNumber {
    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)

    class Base(
        private val numbersDao: NumbersDao,
        private val mapperNumberDataToCache: NumberData.Mapper<NumberCache>
    ) : NumbersCacheDataSource {
        private val mutex = Mutex()

        override suspend fun allNumbers(): List<NumberData> = mutex.withLock {
            return numbersDao.allNumbers().map { NumberData(it.number, it.fact) }
        }

        override suspend fun contains(number: String): Boolean = mutex.withLock {
            return numbersDao.number(number) != null
        }

        override suspend fun saveNumber(numberData: NumberData) = mutex.withLock {
            numbersDao.saveNumber(numberData.map(mapperNumberDataToCache))
        }

        override suspend fun number(number: String): NumberData = mutex.withLock {
            val objNum = numbersDao.number(number) ?: NumberCache("", "", 0)
            return NumberData(objNum.number, objNum.fact)
        }
    }
}

