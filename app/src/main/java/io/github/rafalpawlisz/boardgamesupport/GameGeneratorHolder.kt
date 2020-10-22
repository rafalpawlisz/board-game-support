package io.github.rafalpawlisz.boardgamesupport

import java.util.*

class GameGeneratorHolder {
    private val random = Random()

    private fun getRandomNumber(min: Int, max: Int) = random.nextInt(max) + min
}