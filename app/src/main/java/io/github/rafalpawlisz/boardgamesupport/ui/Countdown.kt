package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.rafalpawlisz.boardgamesupport.R
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel

/**
 * The remaining seconds shown at both ends of the screen — the top one upside down —
 * so two players facing each other across the table can both read it, and either can
 * tap to start or stop the countdown.
 */
@Composable
fun Countdown(
    viewModel: TimerViewModel,
) {
    Box(Modifier.fillMaxSize()) {
        CountdownButton(
            viewModel = viewModel,
            modifier = Modifier
                .padding(bottom = 200.dp)
                .align(Alignment.BottomCenter),
        )
        CountdownButton(
            viewModel = viewModel,
            modifier = Modifier
                .padding(top = 200.dp)
                .align(Alignment.TopCenter)
                .rotate(180f),
        )
    }
}

/**
 * A button rather than a bare number, so it is obvious the counter can be tapped, and
 * filled vs outlined tells at a glance whether the countdown is already running.
 */
@Composable
private fun CountdownButton(
    viewModel: TimerViewModel,
    modifier: Modifier = Modifier,
) {
    if (viewModel.isRunning) {
        OutlinedButton(
            onClick = { viewModel.onCounterClicked() },
            modifier = modifier,
        ) {
            CountdownContent(viewModel)
        }
    } else {
        FilledTonalButton(
            onClick = { viewModel.onCounterClicked() },
            modifier = modifier,
        ) {
            CountdownContent(viewModel)
        }
    }
}

@Composable
private fun CountdownContent(
    viewModel: TimerViewModel,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = viewModel.remainingTime.toString(),
            fontSize = 50.sp,
        )
        Text(
            text = stringResource(if (viewModel.isRunning) R.string.stop else R.string.start),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
        )
    }
}
