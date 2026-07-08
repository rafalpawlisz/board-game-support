package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
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
        Haptics.confirm()
        countdownJob?.let {
            clearCountdown()
            return
        }
        countdownJob = viewModelScope.launch {
            while (remainingTime > 0) {
                remainingTime--
                if (remainingTime in 1..TICK_FROM_SECONDS) {
                    tick()
                }
                delay(1.seconds)
            }
            signalFinish()
            clearCountdown()
        }
    }

    // Triple beep alongside the triple haptic pulse; the counter keeps showing 0
    // for the duration, so a glance at the phone still says the time is up.
    private suspend fun signalFinish() {
        Haptics.finish()
        repeat(3) {
            ToneGenerator.startTone()
            delay(500.milliseconds)
        }
    }

    // A short cue on each of the final seconds, so players feel the clock
    // running out before the finish signal.
    private fun tick() {
        ToneGenerator.startTone()
        Haptics.tick()
    }

    private fun clearCountdown() {
        countdownJob?.cancel()
        countdownJob = null
        remainingTime = startTime
    }

    private companion object {
        const val TICK_FROM_SECONDS = 5
    }
}