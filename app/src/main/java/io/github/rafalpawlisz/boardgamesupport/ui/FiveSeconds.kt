package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import org.koin.androidx.compose.koinViewModel

/** The whole game: name your three answers before the five seconds run out. */
private const val ROUND_SECONDS = 5

@Composable
fun FiveSeconds(
    viewModel: TimerViewModel = koinViewModel(),
) {
    KeepScreenOn()
    LaunchedEffect(Unit) {
        viewModel.useStartTime(ROUND_SECONDS)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Nothing else on screen, so the counters take a half each: whoever is answering
        // and whoever is asking both have one the right way up in front of them.
        Countdown(viewModel)
    }
}
