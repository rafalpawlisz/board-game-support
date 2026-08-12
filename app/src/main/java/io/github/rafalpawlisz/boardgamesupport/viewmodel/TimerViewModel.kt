package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    /** Choosing a duration always restarts the countdown, even the one already chosen. */
    var startTime = 30
        set(value) {
            field = value
            reset()
        }
    var remainingTime by mutableIntStateOf(startTime)
        private set

    /** Drives the counter's label, so a glance says whether tapping starts or stops it. */
    var isRunning by mutableStateOf(false)
        private set
    private var countdownJob: Job? = null

    fun onCounterClicked() {
        ToneGenerator.startTone()
        Haptics.confirm()
        countdownJob?.let {
            reset()
            return
        }
        isRunning = true
        countdownJob = viewModelScope.launch {
            while (remainingTime > 0) {
                remainingTime--
                if (remainingTime in 1..TICK_FROM_SECONDS) {
                    tick()
                }
                delay(1.seconds)
            }
            signalFinish()
            reset()
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

    /**
     * Sets the duration a screen is built around, leaving a running countdown alone when
     * it is already that duration. Screens assign this from a LaunchedEffect, which runs
     * again whenever the activity is recreated — on rotation, dark mode, font size or
     * multi-window changes — and that must not throw away a countdown in progress.
     */
    fun useStartTime(seconds: Int) {
        if (startTime == seconds) return
        startTime = seconds
    }

    /** Stops the countdown and puts the counter back to the start time. */
    fun reset() {
        countdownJob?.cancel()
        countdownJob = null
        isRunning = false
        remainingTime = startTime
    }

    private companion object {
        const val TICK_FROM_SECONDS = 5
    }
}