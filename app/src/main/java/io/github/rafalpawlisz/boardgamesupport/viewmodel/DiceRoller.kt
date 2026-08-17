package io.github.rafalpawlisz.boardgamesupport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * Animates a roll by briefly cycling through random interim values before settling
 * on the final result. The final value is drawn up front, so the animation is purely
 * cosmetic and does not affect the distribution. Starting a new roll cancels the
 * previous one.
 */
class DiceRoller(private val scope: CoroutineScope) {
    private var rollJob: Job? = null

    /**
     * True while the value on screen belongs to the animation rather than to the roll.
     * Anything read off an interim value — a total, say — is not a result yet.
     */
    var isRolling by mutableStateOf(false)
        private set

    fun <T> roll(randomValue: () -> T, onValue: (T) -> Unit) {
        cancel()
        isRolling = true
        rollJob = scope.launch {
            val finalValue = randomValue()
            var shown: T? = null
            for (tickDelay in TICK_DELAYS) {
                shown = nextInterim(randomValue, shown)
                onValue(shown)
                Haptics.tick()
                delay(tickDelay)
            }
            // Settled before the value is handed over, so no frame shows the result
            // and the animation's state at the same time.
            isRolling = false
            onValue(finalValue)
            ToneGenerator.startTone()
            Haptics.confirm()
        }
    }

    /** Stops an in-flight roll, so a cancelled animation cannot overwrite the state. */
    fun cancel() {
        rollJob?.cancel()
        rollJob = null
        isRolling = false
    }

    /**
     * Picks a value different from the currently shown one when possible,
     * so the slow final ticks don't look frozen.
     */
    private fun <T> nextInterim(randomValue: () -> T, current: T?): T {
        repeat(4) {
            val value = randomValue()
            if (value != current) return value
        }
        return randomValue()
    }

    private companion object {
        // Ticks slow down toward the end, like a die coming to rest.
        val TICK_DELAYS = listOf(50, 60, 70, 90, 110, 140, 180).map { it.milliseconds }
    }
}
