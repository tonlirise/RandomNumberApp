package com.example.randomnumberapp.numbers.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomnumberapp.numbers.data.NumberData

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers_table ORDER BY date ASC")
    fun allNumbers() : List<NumberCache>

    @Query("SELECT * FROM numbers_table WHERE number = :num")
    fun number(num: String): NumberCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNumber(number: NumberCache)
}