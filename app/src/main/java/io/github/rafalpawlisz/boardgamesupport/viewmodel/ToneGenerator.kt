package io.github.rafalpawlisz.boardgamesupport.viewmodel

import android.media.AudioManager
import android.media.ToneGenerator as AndroidToneGenerator

object ToneGenerator {
    // Created lazily on first use. The native ToneGenerator constructor can throw a
    // RuntimeException on some devices, so failure degrades to a silent no-op instead of a crash.
    private val toneGenerator: AndroidToneGenerator? by lazy {
        runCatching {
            AndroidToneGenerator(
                AudioManager.STREAM_MUSIC,
                AndroidToneGenerator.MAX_VOLUME,
            )
        }.getOrNull()
    }

    fun startTone() {
        toneGenerator?.startTone(
            AndroidToneGenerator.TONE_CDMA_MED_SSL,
            200,
        )
    }
}
