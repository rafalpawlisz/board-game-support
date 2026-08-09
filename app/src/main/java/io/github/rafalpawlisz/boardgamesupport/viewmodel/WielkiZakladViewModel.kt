package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class WielkiZakladViewModel : ViewModel() {
    var result by mutableStateOf<Result>(TextResult(""))
        private set
    private var firstClick = true
    private val roller = DiceRoller(viewModelScope)

    fun generateResult() {
        val randomValue: () -> Result = if (firstClick) {
            firstClick = false
            { ColorResult(COLORS.random()) }
        } else {
            { TextResult((1..3).random().toString()) }
        }
        roller.roll(randomValue) { result = it }
    }

    /** Starts a new game, so the next draw is a colour again. */
    fun newGame() {
        roller.cancel()
        firstClick = true
        result = TextResult("")
    }

    sealed interface Result
    data class TextResult(val text: String) : Result
    data class ColorResult(val color: Color) : Result

    private companion object {
        val COLORS = listOf(Color.Green, Color.Yellow, Color(0xFFFF8C00))
    }
}
