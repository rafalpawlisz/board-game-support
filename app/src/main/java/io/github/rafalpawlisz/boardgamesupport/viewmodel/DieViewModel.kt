package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DieViewModel : ViewModel() {
    var result by mutableStateOf("")
        private set

    fun generateResult() {
        result = (1..6).random().toString()
        ToneGenerator.startTone()
    }
}