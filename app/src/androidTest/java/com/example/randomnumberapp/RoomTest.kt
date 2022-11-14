package com.example.randomnumberapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.randomnumberapp.numbers.data.cache.NumberCache
import com.example.randomnumberapp.numbers.data.cache.NumbersDao
import com.example.randomnumberapp.numbers.data.cache.NumbersDataBase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: NumbersDataBase
    private lateinit var dao: NumbersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NumbersDataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.getNumbersDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_add_and_check() {
        val number = NumberCache("404", "fact404", 12)
        assertEquals(null, dao.number("404"))
        dao.saveNumber(number)
        val actualList = dao.allNumbers()
        assertEquals(number, actualList[0])
        val actual = dao.number("42")
        assertEquals(number, actual)
    }

    @Test
    fun test_add_2_times() {
        val number = NumberCache("404", "fact404", 12)
        dao.saveNumber(number)
        var actualList = dao.allNumbers()
        assertEquals(number, actualList[0])

        val new = NumberCache("404", "fact404", 15)
        dao.saveNumber(new)
        actualList = dao.allNumbers()
        assertEquals(1, actualList.size)
        assertEquals(new, actualList[0])
    }
}