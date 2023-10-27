package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WielkiZakladViewModel : ViewModel() {
    var result by mutableStateOf("")
        private set

    private var firstClick by mutableStateOf(true)

    fun generateResult() {
        result = if (firstClick) {
            firstClick = false
            listOf("green", "orange", "yellow").random()
        } else {
            (1..3).random().toString()
        }
        ToneGenerator.startTone()
    }
}