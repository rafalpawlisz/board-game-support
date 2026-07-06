package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class DieViewModel : ViewModel() {
    var result by mutableStateOf("")
        private set
    private val roller = DiceRoller(viewModelScope)

    fun generateResult() {
        roller.roll(randomValue = { (1..6).random().toString() }) { result = it }
    }
}
