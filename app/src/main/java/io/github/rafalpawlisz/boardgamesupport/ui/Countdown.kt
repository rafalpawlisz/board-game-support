package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import io.github.rafalpawlisz.boardgamesupport.R
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel

/** Half of a rolled result: read across the table just the same, but the result leads. */
private const val NUMBER_FRACTION = 0.125f

/** Of the number beside it, so the label never dwarfs or disappears against it. */
private const val LABEL_FRACTION = 0.28f
private const val LETTER_SPACING_FRACTION = 0.107f

/**
 * The remaining seconds shown twice, one copy upside down, so two players facing each
 * other across the table can both read it, and either can tap to start or stop it.
 *
 * Each counter sits halfway between the middle of the screen and its own edge, and its
 * size follows the screen, so the spacing and the digits suit any device rather than the
 * one they were first tuned on.
 *
 * @param sidesInLandscape places the counters left and right in landscape rather than
 * top and bottom, which keeps the short middle free for whatever the screen shows there.
 * @param reservedCenter how much of the middle is taken by that something — the counters
 * centre themselves in what is left on their side.
 */
@Composable
fun Countdown(
    viewModel: TimerViewModel,
    sidesInLandscape: Boolean = false,
    reservedCenter: Dp = 0.dp,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val numberSize = min(maxWidth, maxHeight) * NUMBER_FRACTION

        if (maxWidth > maxHeight && sidesInLandscape) {
            Row(Modifier.fillMaxSize()) {
                CounterSlot(Modifier.weight(1f).fillMaxHeight()) {
                    CountdownButton(viewModel, numberSize, Modifier.rotate(180f))
                }
                Spacer(Modifier.width(reservedCenter))
                CounterSlot(Modifier.weight(1f).fillMaxHeight()) {
                    CountdownButton(viewModel, numberSize)
                }
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                CounterSlot(Modifier.weight(1f).fillMaxWidth()) {
                    CountdownButton(viewModel, numberSize, Modifier.rotate(180f))
                }
                Spacer(Modifier.height(reservedCenter))
                CounterSlot(Modifier.weight(1f).fillMaxWidth()) {
                    CountdownButton(viewModel, numberSize)
                }
            }
        }
    }
}

@Composable
private fun CounterSlot(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        content()
    }
}

/**
 * A button rather than a bare number, so it is obvious the counter can be tapped, and
 * filled vs outlined tells at a glance whether the countdown is already running.
 */
@Composable
private fun CountdownButton(
    viewModel: TimerViewModel,
    numberSize: Dp,
    modifier: Modifier = Modifier,
) {
    if (viewModel.isRunning) {
        OutlinedButton(
            onClick = { viewModel.onCounterClicked() },
            modifier = modifier,
        ) {
            CountdownContent(viewModel, numberSize)
        }
    } else {
        FilledTonalButton(
            onClick = { viewModel.onCounterClicked() },
            modifier = modifier,
        ) {
            CountdownContent(viewModel, numberSize)
        }
    }
}

@Composable
private fun CountdownContent(
    viewModel: TimerViewModel,
    numberSize: Dp,
) {
    val labelSize = (numberSize * LABEL_FRACTION).toFontSize()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = viewModel.remainingTime.toString(),
            fontSize = numberSize.toFontSize(),
        )
        Text(
            text = stringResource(if (viewModel.isRunning) R.string.stop else R.string.start),
            fontSize = labelSize,
            fontWeight = FontWeight.Bold,
            letterSpacing = labelSize * LETTER_SPACING_FRACTION,
        )
    }
}
