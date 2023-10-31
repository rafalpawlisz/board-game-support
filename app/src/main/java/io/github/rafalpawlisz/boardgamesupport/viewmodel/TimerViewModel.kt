package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    var startTime = 30
        set(value) {
            field = value
            clearCountdown()
        }
    var remainingTime by mutableIntStateOf(startTime)
        private set
    private var countdownJob: Job? = null

    fun onCounterClicked() {
        ToneGenerator.startTone()
        countdownJob?.let {
            clearCountdown()
            return
        }
        countdownJob = viewModelScope.launch {
            while (remainingTime > 0) {
                remainingTime--
                delay(1.seconds)
            }
            ToneGenerator.startTone()
            clearCountdown()
        }
    }

    private fun clearCountdown() {
        countdownJob?.cancel()
        countdownJob = null
        remainingTime = startTime
    }
}