package com.example.randomnumberapp.numbers.data.cache

import com.example.randomnumberapp.numbers.data.NumberData

class MapperNumberDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String) = NumberCache(id, fact, System.currentTimeMillis())
}