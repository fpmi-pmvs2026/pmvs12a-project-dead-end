package com.example.lab8

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.abs
import kotlin.random.Random

class ExampleUnitTest {

    // ===== БАЗОВЫЕ ТЕСТЫ =====

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subtraction_isCorrect() {
        assertEquals(5, 10 - 5)
    }

    @Test
    fun multiplication_isCorrect() {
        assertEquals(15, 3 * 5)
    }

    @Test
    fun division_isCorrect() {
        assertEquals(4, 16 / 4)
    }

    // ===== ТЕСТЫ ДЛЯ ИГРОВОЙ ЛОГИКИ =====

    @Test
    fun testPlatformGeneration() {
        val platforms = ArrayDeque<Pair<Float, Float>>()

        fun addRandomPlatform(y: Float) {
            val randomX = Random.nextFloat() * 1000f
            platforms.addLast(Pair(randomX, y))
        }

        addRandomPlatform(100f)
        addRandomPlatform(200f)

        assertEquals(2, platforms.size)
        assertEquals(100f, platforms[0].second)
        assertEquals(200f, platforms[1].second)
        assertTrue(platforms[0].first in 0f..1000f)
    }

    @Test
    fun testPlatformRemoval() {
        val platforms = ArrayDeque<Pair<Float, Float>>()
        platforms.addLast(Pair(100f, 100f))
        platforms.addLast(Pair(200f, 200f))
        platforms.addLast(Pair(300f, 300f))

        platforms.removeFirst()

        assertEquals(2, platforms.size)
        assertEquals(200f, platforms[0].first)
        assertEquals(200f, platforms[0].second)
    }

    @Test
    fun testScoreCalculation() {
        var record = 0
        val yCord1 = 1500f
        val yCord2 = 3000f
        val yCord3 = 5000f

        fun calculateScore(yCord: Float): Int {
            return maxOf(record, ((yCord - 300) / 600).toInt() - 2)
        }

        val score1 = calculateScore(yCord1)
        val score2 = calculateScore(yCord2)
        val score3 = calculateScore(yCord3)

        assertEquals(0, score1)
        assertEquals(2, score2)
        assertEquals(5, score3)
    }

    @Test
    fun testRecordMaximization() {
        var record = 0
        val newScore1 = 10
        val newScore2 = 5
        val newScore3 = 15

        record = maxOf(record, newScore1)
        assertEquals(10, record)

        record = maxOf(record, newScore2)
        assertEquals(10, record)

        record = maxOf(record, newScore3)
        assertEquals(15, record)
    }

    // ===== ТЕСТЫ ДЛЯ ФИЗИКИ ПРЫЖКОВ =====

    @Test
    fun testJumpPhysics() {
        var jumpV = 0f
        var jumpA = 270f
        val g = 9.8f

        // Симуляция прыжка
        jumpV += jumpA * 0.1f
        assertEquals(27f, jumpV, 0.01f)

        // Изменение ускорения
        jumpA -= g * 0.5f
        assertEquals(265.1f, jumpA, 0.01f)
    }

    @Test
    fun testGravityEffect() {
        var jumpA = 270f
        val g = 9.8f

        for (i in 1..10) {
            jumpA -= g * 0.5f
        }

        assertTrue(jumpA < 270f)
        assertEquals(221f, jumpA, 0.1f)
    }

    @Test
    fun testVelocityCalculation() {
        var v = 0f
        var a = 5f

        v += a
        assertEquals(5f, v, 0.01f)

        v += a
        assertEquals(10f, v, 0.01f)

        v = v.coerceIn(-50f, 50f)
        assertEquals(10f, v, 0.01f)

        v = 100f
        v = v.coerceIn(-50f, 50f)
        assertEquals(50f, v, 0.01f)
    }

    // ===== ТЕСТЫ ДЛЯ КОЛЛИЗИЙ =====

    @Test
    fun testCollisionDetection() {
        val playerX = 200f
        val platformX = 180f
        val playerWidth = 80f
        val collisionDistance = 130f

        val isColliding = abs(playerX - platformX - playerWidth) <= collisionDistance

        assertTrue(isColliding)
    }

    @Test
    fun testCollisionDetection_NoCollision() {
        val playerX = 500f
        val platformX = 180f
        val playerWidth = 80f
        val collisionDistance = 130f

        val isColliding = abs(playerX - platformX - playerWidth) <= collisionDistance

        assertFalse(isColliding)
    }

    @Test
    fun testMultiplePlatformCollision() {
        val playerX = 200f
        val platforms = listOf(
            100f, 200f, 300f, 400f, 500f
        )
        val playerWidth = 80f
        val collisionDistance = 130f

        var collisionDetected = false
        for (platformX in platforms) {
            if (abs(playerX - platformX - playerWidth) <= collisionDistance) {
                collisionDetected = true
                break
            }
        }

        assertTrue(collisionDetected)
    }

    // ===== ТЕСТЫ ДЛЯ ПОГОДНЫХ УСЛОВИЙ =====

    @Test
    fun testTemperatureToWarmCondition() {
        val tempCelsius = 20f
        val isWarm = tempCelsius > 15f

        assertTrue(isWarm)
    }

    @Test
    fun testTemperatureToColdCondition() {
        val tempCelsius = 10f
        val isWarm = tempCelsius > 15f

        assertFalse(isWarm)
    }

    @Test
    fun testHumidityToRainCondition() {
        val humidity = 75
        val isRainy = humidity > 70

        assertTrue(isRainy)
    }

    @Test
    fun testHumidityToNoRainCondition() {
        val humidity = 50
        val isRainy = humidity > 70

        assertFalse(isRainy)
    }

    @Test
    fun testKelvinToCelsius() {
        val kelvin = 293.15
        val celsius = kelvin - 273.15

        assertEquals(20.0, celsius, 0.01)
    }

    // ===== ТЕСТЫ ДЛЯ ДАННЫХ =====

    @Test
    fun testPairDataStructure() {
        val record = Pair(100, "Player1")

        assertEquals(100, record.first)
        assertEquals("Player1", record.second)
    }

    @Test
    fun testListOfRecords() {
        val records = mutableListOf<Pair<Int, String>>()
        records.add(Pair(100, "Player1"))
        records.add(Pair(200, "Player2"))
        records.add(Pair(150, "Player3"))

        // Сортировка по убыванию
        val sorted = records.sortedByDescending { it.first }

        assertEquals(200, sorted[0].first)
        assertEquals(150, sorted[1].first)
        assertEquals(100, sorted[2].first)
    }

    @Test
    fun testTop5Limit() {
        val records = mutableListOf<Pair<Int, String>>()

        for (i in 1..10) {
            records.add(Pair(i * 10, "Player$i"))
        }

        val top5 = records.sortedByDescending { it.first }.take(5)

        assertEquals(5, top5.size)
        assertEquals(100, top5[0].first)
        assertEquals(90, top5[1].first)
        assertEquals(80, top5[2].first)
        assertEquals(70, top5[3].first)
        assertEquals(60, top5[4].first)
    }

    // ===== ТЕСТЫ ДЛЯ СЛУЧАЙНЫХ ВЕЛИЧИН =====

    @Test
    fun testRandomXGeneration() {
        val randomValues = mutableListOf<Float>()

        repeat(100) {
            val randomX = Random.nextFloat() * 1000f
            randomValues.add(randomX)
        }

        // Проверяем, что все значения в диапазоне
        randomValues.forEach {
            assertTrue(it in 0f..1000f)
        }

        // Проверяем, что не все значения одинаковые
        assertTrue(randomValues.distinct().size > 1)
    }

    @Test
    fun testRandomness() {
        val value1 = Random.nextInt(0, 100)
        val value2 = Random.nextInt(0, 100)

        // Не гарантируем, что они разные, но с высокой вероятностью
        // Этот тест может иногда проваливаться, но это нормально
        assertTrue(value1 in 0..100)
        assertTrue(value2 in 0..100)
    }

    // ===== ТЕСТЫ ДЛЯ СТРОК =====

    @Test
    fun testPlayerNameValidation() {
        val validName = "Player123"
        val emptyName = ""
        val longName = "VeryLongPlayerNameThatExceedsNormalLength"

        assertTrue(validName.isNotEmpty())
        assertTrue(emptyName.isEmpty())
        assertTrue(longName.length > 10)
    }

    @Test
    fun testScoreToString() {
        val score = 250
        val scoreString = "Score: $score"

        assertEquals("Score: 250", scoreString)
    }

    // ===== ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ =====

    @Test
    fun testMultipleScoreValues() {
        val scores = listOf(10, 50, 100, 250, 500, 1000)
        val expectedResults = listOf(10, 50, 100, 250, 500, 1000)

        scores.forEachIndexed { index, score ->
            assertEquals(expectedResults[index], score)
        }
    }

    @Test
    fun testBoundaryValues() {
        val testCases = listOf(
            0 to 0,
            -5 to -5,
            9999 to 9999,
            Int.MAX_VALUE to Int.MAX_VALUE
        )

        testCases.forEach { (input, expected) ->
            assertEquals(expected, input)
        }
    }

    // ===== ТЕСТЫ ДЛЯ ВРЕМЕНИ =====

    @Test
    fun testDeltaTime() {
        val deltaTime = 0.1f
        val expectedDelta = 0.1f

        assertEquals(expectedDelta, deltaTime, 0.01f)
    }

    @Test
    fun testJumpTiming() {
        var time = 0f
        val timeStep = 0.1f

        repeat(10) {
            time += timeStep
        }

        assertEquals(1.0f, time, 0.01f)
    }

    // ===== ТЕСТЫ ДЛЯ ОЧИСТКИ ДАННЫХ =====

    @Test
    fun testDataClearing() {
        val dataList = mutableListOf(1, 2, 3, 4, 5)

        assertTrue(dataList.isNotEmpty())

        dataList.clear()

        assertTrue(dataList.isEmpty())
    }

    @Test
    fun testQueueOperations() {
        val queue = ArrayDeque<Int>()

        queue.addLast(1)
        queue.addLast(2)
        queue.addLast(3)

        assertEquals(3, queue.size)

        val first = queue.removeFirst()
        assertEquals(1, first)
        assertEquals(2, queue.size)
    }
}

// Вспомогательная функция
private fun maxOf(a: Int, b: Int): Int = if (a > b) a else b