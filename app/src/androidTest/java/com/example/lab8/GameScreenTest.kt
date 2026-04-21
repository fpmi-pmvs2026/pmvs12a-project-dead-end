package com.example.lab8

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameScreenTest {

    @Test
    fun testGameLogic() {
        // Простая проверка без Compose UI
        val platforms = ArrayDeque<Pair<Float, Float>>()
        platforms.addLast(Pair(100f, 100f))

        assert(platforms.isNotEmpty())
        assert(platforms[0].first == 100f)
    }

    @Test
    fun testScoreCalculation() {
        var record = 0
        val yCord = 3000f
        val newScore = ((yCord - 300) / 600).toInt() - 2

        assert(newScore == 2)
    }
}