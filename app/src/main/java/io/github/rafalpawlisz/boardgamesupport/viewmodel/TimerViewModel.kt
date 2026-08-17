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
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * @param startTime how long a round lasts. Fixed when the screen asks for its timer, so
 * the counter never shows a duration belonging to some other game.
 */
class TimerViewModel(
    val startTime: Int,
) : ViewModel() {
    var remainingTime by mutableIntStateOf(startTime)
        private set

    /** Drives the counter's label, so a glance says whether tapping starts or stops it. */
    var isRunning by mutableStateOf(false)
        private set

    private var countdownJob: Job? = null
    private var finishJob: Job? = null

    /** A round shorter than the closing stretch ticks all the way through. */
    private val tickFrom = min(TICK_WINDOW_SECONDS, startTime)

    fun onCounterClicked() {
        ToneGenerator.startTone()
        Haptics.confirm()
        if (isRunning) {
            reset()
        } else {
            start()
        }
    }

    /** Stops the countdown and puts the counter back to the start time. */
    fun reset() {
        countdownJob?.cancel()
        countdownJob = null
        finishJob?.cancel()
        finishJob = null
        isRunning = false
        remainingTime = startTime
    }

    private fun start() {
        // A buzzer still sounding from the last round gives way to this one.
        finishJob?.cancel()
        finishJob = null
        remainingTime = startTime
        isRunning = true
        countdownJob = viewModelScope.launch {
            // Wait first, then count down, so the full duration is on screen for its own
            // second. Counting down first would skip it and leave zero showing early —
            // barely noticeable over 30 seconds, glaring over five.
            while (remainingTime > 0) {
                delay(1.seconds)
                remainingTime--
                if (remainingTime in 1..tickFrom) {
                    tick()
                }
            }
            // The round ends the moment it reaches zero. The buzzer outlives it as its own
            // job, so tapping to start the next round is never spent silencing this one.
            countdownJob = null
            isRunning = false
            finishJob = viewModelScope.launch {
                signalFinish()
                remainingTime = startTime
                finishJob = null
            }
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

    private companion object {
        const val TICK_WINDOW_SECONDS = 5
    }
}
