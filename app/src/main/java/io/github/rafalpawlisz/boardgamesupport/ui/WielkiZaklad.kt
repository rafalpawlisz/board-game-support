package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val landscape = maxWidth > maxHeight

        FourValues { size ->
            when (val result = viewModel.result) {
                is WielkiZakladViewModel.ColorResult -> Box(
                    Modifier
                        .size(size)
                        .background(result.color)
                )
                is WielkiZakladViewModel.TextResult -> Text(
                    text = result.text,
                    fontSize = size.toFontSize(),
                )
            }
        }
        PlayButton {
            viewModel.generateResult()
        }
        // Started by hand: whether a draw is followed by the 60 seconds is decided
        // at the table, so the app never starts the countdown on its own. In landscape
        // the counters move to the sides, leaving the short middle to the PLAY button.
        Countdown(
            viewModel = timerViewModel,
            sidesInLandscape = true,
            reservedCenter = PlayButtonDiameter,
        )
        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = if (landscape) 16.dp else 40.dp),
            onClick = {
                viewModel.newGame()
                timerViewModel.reset()
            },
        ) {
            Text(text = stringResource(R.string.new_game))
        }
    }
}
