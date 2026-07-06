package io.github.rafalpawlisz.boardgamesupport.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Haptic feedback that works regardless of the ringer volume. Vibrating is
 * best-effort; missing hardware or failures degrade to a silent no-op.
 */
object Haptics {
    private var vibrator: Vibrator? = null

    fun init(context: Context) {
        vibrator = runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        }.getOrNull()?.takeIf { it.hasVibrator() }
    }

    // The EFFECT_* constants are compile-time inlined, and predefined() only hands
    // them to the platform on API 29+, so referencing them here is safe on minSdk 24.

    /** Subtle pulse for each roll animation tick. */
    @SuppressLint("InlinedApi")
    fun tick() = predefined(VibrationEffect.EFFECT_TICK, fallbackMillis = 10)

    /** Clear single pulse for a settled result or a pressed control. */
    @SuppressLint("InlinedApi")
    fun confirm() = predefined(VibrationEffect.EFFECT_HEAVY_CLICK, fallbackMillis = 50)

    /** Hard-to-miss triple pulse for a finished countdown. */
    fun finish() = vibrate { vibrator ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(longArrayOf(0, 150, 100, 150, 100, 150), -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(650)
        }
    }

    private fun predefined(effectId: Int, fallbackMillis: Long) = vibrate { vibrator ->
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
                vibrator.vibrate(VibrationEffect.createPredefined(effectId))
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                vibrator.vibrate(
                    VibrationEffect.createOneShot(fallbackMillis, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            else -> {
                @Suppress("DEPRECATION")
                vibrator.vibrate(fallbackMillis)
            }
        }
    }

    private fun vibrate(block: (Vibrator) -> Unit) {
        val vibrator = vibrator ?: return
        runCatching { block(vibrator) }
    }
}
