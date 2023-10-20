package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    private var startTime by mutableIntStateOf(30)
    private var counting by mutableStateOf(false)
    var remainingTime by mutableIntStateOf(startTime)
        private set

    fun onCounterClicked(startTone: () -> Unit) {
        if (counting) {
            counting = false
        } else {
            counting = true
            startTone()
            viewModelScope.launch {
                while (remainingTime > 0 && counting) {
                    remainingTime--
                    delay(1.seconds)
                }
                counting = false
                startTone()
                remainingTime = startTime
            }
        }
    }

    fun setNewTime(value: Int) {
        startTime = value
        remainingTime = value
    }
}