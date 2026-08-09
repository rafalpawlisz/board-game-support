package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Timer(
    viewModel: TimerViewModel = koinViewModel(),
) {
    KeepScreenOn()
    Box(Modifier.fillMaxSize()) {
        Countdown(viewModel)
        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 50.dp),
            onClick = {
                viewModel.startTime = 30
            },
        ) {
            Text(text = "30")
        }

        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 50.dp),
            onClick = {
                viewModel.startTime = 60
            },
        ) {
            Text(text = "60")
        }
    }
}
