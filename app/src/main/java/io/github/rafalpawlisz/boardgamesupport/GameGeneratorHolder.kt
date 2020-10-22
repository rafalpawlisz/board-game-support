package io.github.rafalpawlisz.boardgamesupport

import java.util.*

class GameGeneratorHolder {
    private val random = Random()

    fun getCubeGameGenerator() = GameGenerator { getRandomNumber(1, 6).toString() }

    private fun getRandomNumber(min: Int, max: Int) = random.nextInt(max) + min
}