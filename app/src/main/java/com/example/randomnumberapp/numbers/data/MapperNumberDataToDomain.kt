package com.example.randomnumberapp.numbers.data

import com.example.randomnumberapp.numbers.domain.NumberFact

class MapperNumberDataToDomain : NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String) = NumberFact(id, fact)
}