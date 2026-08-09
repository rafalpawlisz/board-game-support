package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.rafalpawlisz.boardgamesupport.R
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import io.github.rafalpawlisz.boardgamesupport.viewmodel.WielkiZakladViewModel
import org.koin.androidx.compose.koinViewModel

/** The game measures 60 seconds after some of the draws. */
private const val ROUND_SECONDS = 60

@Composable
fun WielkiZaklad(
    viewModel: WielkiZakladViewModel = koinViewModel(),
    timerViewModel: TimerViewModel = koinViewModel(),
) {
    KeepScreenOn()
    LaunchedEffect(Unit) {
        timerViewModel.startTime = ROUND_SECONDS
    }
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues {
            when (val result = viewModel.result) {
                is WielkiZakladViewModel.ColorResult -> Box(
                    Modifier
                        .size(100.dp)
                        .background(result.color)
                )
                is WielkiZakladViewModel.TextResult -> Text(
                    text = result.text,
                    fontSize = 100.sp,
                )
            }
        }
        PlayButton {
            viewModel.generateResult()
        }
        // Started by hand: whether a draw is followed by the 60 seconds is decided
        // at the table, so the app never starts the countdown on its own.
        Countdown(timerViewModel)
        // Below the near-side counter: the centre and both sides are taken by the
        // PLAY button and the corner values.
        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            onClick = {
                viewModel.newGame()
                timerViewModel.startTime = ROUND_SECONDS
            },
        ) {
            Text(text = stringResource(R.string.new_game))
        }
    }
}
