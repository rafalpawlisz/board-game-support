package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.runtime.Composable
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/** The whole game: name your three answers before the five seconds run out. */
private const val ROUND_SECONDS = 5

@Composable
fun FiveSeconds(
    viewModel: TimerViewModel = koinViewModel { parametersOf(ROUND_SECONDS) },
) {
    KeepScreenOn()
    // Nothing else on screen, so the counters take a half each: whoever is answering and
    // whoever is asking both have one the right way up in front of them.
    Countdown(viewModel)
}
