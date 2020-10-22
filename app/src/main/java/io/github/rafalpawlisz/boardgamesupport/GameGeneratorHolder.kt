package io.github.rafalpawlisz.boardgamesupport

import java.util.*

class GameGeneratorHolder {
    private val random = Random()

    fun getCubeGameGenerator() = GameGenerator { getRandomNumber(1, 6).toString() }

    fun getImagoGameGenerator() = GameGenerator { getRandomNumber(1, 8).toString() }

    fun getWielkiZakladGameGenerator() = object : GameGenerator {
        var firstGeneration = true

        override fun generate(): String {
            if (firstGeneration) {
                firstGeneration = false
                return listOf("green", "orange", "yellow")[getRandomNumber(0, 2)]
            }
            return getRandomNumber(1, 3).toString()
        }
    }

    private fun getRandomNumber(min: Int, max: Int) = random.nextInt(max) + min
}