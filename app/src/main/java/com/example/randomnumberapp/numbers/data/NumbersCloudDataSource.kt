package com.example.randomnumberapp.numbers.data

interface NumbersCloudDataSource : FetchNumber {
    suspend fun randomNumber(): NumberData

    class Base(private val service: NumberService) : NumbersCloudDataSource {
        override suspend fun number(number: String): NumberData {
            val fact = service.number(number)
            return NumberData(number, fact)
        }

        override suspend fun randomNumber(): NumberData {
            val response = service.randomNumber()

            val num = response.headers()
                .find { (key, _) -> key == API_NUMBER }
                ?.let { (_, value) -> value }
                ?: throw IllegalStateException("service unavailable")

            val fact = response.body()
                ?: throw IllegalStateException("service unavailable")

            return NumberData(num, fact)
        }

        companion object {
            private val API_NUMBER = "X-Numbers-API-Number"
        }
    }
}
