package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

/** Catan is played off the total of two ordinary dice. */
class CatanViewModel : ViewModel() {
    var dice by mutableStateOf(emptyList<Int>())
        private set
    private val roller = DiceRoller(viewModelScope)

    /** What the table reacts to; the individual dice only show how it was reached. */
    val total: Int
        get() = dice.sum()

    /** While this holds, [dice] is a frame of the animation and [total] means nothing. */
    val isRolling: Boolean
        get() = roller.isRolling

    fun generateResult() {
        roller.roll(randomValue = { List(DICE) { (1..SIDES).random() } }) { dice = it }
    }

    private companion object {
        const val DICE = 2
        const val SIDES = 6
    }
}
