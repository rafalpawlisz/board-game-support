package io.github.rafalpawlisz.boardgamesupport.viewmodel

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

    fun <T> roll(randomValue: () -> T, onValue: (T) -> Unit) {
        rollJob?.cancel()
        rollJob = scope.launch {
            val finalValue = randomValue()
            var shown: T? = null
            for (tickDelay in TICK_DELAYS) {
                shown = nextInterim(randomValue, shown)
                onValue(shown)
                delay(tickDelay)
            }
            onValue(finalValue)
            ToneGenerator.startTone()
        }
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
