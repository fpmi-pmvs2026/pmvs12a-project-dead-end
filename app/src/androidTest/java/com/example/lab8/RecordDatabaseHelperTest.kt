package com.example.lab8

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecordDatabaseHelperTest {

    private lateinit var dbHelper: RecordDatabaseHelper
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        dbHelper = RecordDatabaseHelper(context)
        clearDatabase()
    }

    @After
    fun tearDown() {
        clearDatabase()
        dbHelper.close()
    }

    private fun clearDatabase() {
        val records = dbHelper.getTop5Records()
        // Удаляем все записи
        while (dbHelper.getTop5Records().isNotEmpty()) {
            dbHelper.removeLowestRecord()
        }
    }

    @Test
    fun testInsertRecord() {
        dbHelper.insertRecord(100, "TestPlayer")
        val records = dbHelper.getTop5Records()

        assertTrue(records.isNotEmpty())
        assertEquals(100, records[0].first)
        assertEquals("TestPlayer", records[0].second)
    }

    @Test
    fun testGetTop5Records_ReturnsSortedDescending() {
        clearDatabase()
        dbHelper.insertRecord(50, "Player1")
        dbHelper.insertRecord(200, "Player2")
        dbHelper.insertRecord(150, "Player3")

        val records = dbHelper.getTop5Records()

        assertEquals(3, records.size)
        assertEquals(200, records[0].first)
        assertEquals(150, records[1].first)
        assertEquals(50, records[2].first)
    }

    @Test
    fun testRemoveLowestRecord() {
        clearDatabase()
        dbHelper.insertRecord(100, "Player1")
        dbHelper.insertRecord(50, "Player2")
        dbHelper.insertRecord(75, "Player3")

        // Проверяем, что перед удалением 3 записи
        assertEquals(3, dbHelper.getTop5Records().size)

        dbHelper.removeLowestRecord()
        val records = dbHelper.getTop5Records()

        // После удаления должно быть 2 записи
        assertEquals(2, records.size)
        // Проверяем, что записи со score=50 нет
        assertFalse(records.any { it.first == 50 })
        // Проверяем, что остались 100 и 75
        assertTrue(records.any { it.first == 100 })
        assertTrue(records.any { it.first == 75 })
    }

    @Test
    fun testGetTop5Records_Limit5() {
        clearDatabase()
        for (i in 1..10) {
            dbHelper.insertRecord(i * 10, "Player$i")
        }

        val records = dbHelper.getTop5Records()

        assertEquals(5, records.size)
        assertEquals(100, records[0].first) // Самый высокий
        assertEquals(90, records[1].first)
        assertEquals(80, records[2].first)
    }
}