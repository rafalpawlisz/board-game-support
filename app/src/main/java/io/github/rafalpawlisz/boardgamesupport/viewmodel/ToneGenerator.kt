package io.github.rafalpawlisz.boardgamesupport.viewmodel

import android.media.AudioManager
import android.media.ToneGenerator as AndroidToneGenerator

object ToneGenerator {
    private val toneGenerator = AndroidToneGenerator(
        AudioManager.STREAM_MUSIC,
        AndroidToneGenerator.MAX_VOLUME,
    )

    fun startTone() = toneGenerator.startTone(
        AndroidToneGenerator.TONE_CDMA_MED_SSL,
        200,
    )
}