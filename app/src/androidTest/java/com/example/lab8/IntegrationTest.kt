package com.example.lab8

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegrationTest {

    private lateinit var dbHelper: RecordDatabaseHelper
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        dbHelper = RecordDatabaseHelper(context)

        // Очистка БД перед каждым тестом
        val records = dbHelper.getTop5Records()
        repeat(records.size) {
            dbHelper.removeLowestRecord()
        }
    }

    @Test
    fun testFullGameFlow_SaveHighScore() {
        // 1. Игрок получает рекорд
        val playerScore = 500
        val playerName = "Champion"

        // 2. Сохранение в БД
        dbHelper.insertRecord(playerScore, playerName)

        // 3. Проверка сохранения
        val records = dbHelper.getTop5Records()
        assertTrue(records.any { it.first == playerScore && it.second == playerName })
    }

    @Test
    fun testHighScore_OnlyTop5Saved() {
        // Добавляем 10 рекордов
        for (i in 1..10) {
            dbHelper.insertRecord(i * 10, "Player$i")
        }

        // Удаляем наименьшие до 5 записей
        while (dbHelper.getTop5Records().size > 5) {
            dbHelper.removeLowestRecord()
        }

        val records = dbHelper.getTop5Records()
        assertEquals(5, records.size)

        // Проверяем, что остались только топ-5
        val minScore = records.minOf { it.first }
        assertTrue(minScore >= 60) // 6-й рекорд был 60
    }
}