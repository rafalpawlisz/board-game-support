package io.github.rafalpawlisz.boardgamesupport.viewmodel

import android.graphics.Color.rgb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class WielkiZakladViewModel : ViewModel() {
    var result by mutableStateOf<Result>(TextResult(""))
        private set
    private var firstClick = true

    fun generateResult() {
        result = if (firstClick) {
            firstClick = false
            ColorResult(
                listOf(Color.Green, Color.Yellow, Color(rgb(255, 140, 0))).random()
            )
        } else {
            TextResult((1..3).random().toString())
        }
        ToneGenerator.startTone()
    }

    sealed interface Result
    data class TextResult(val text: String) : Result
    data class ColorResult(val color: Color) : Result
}