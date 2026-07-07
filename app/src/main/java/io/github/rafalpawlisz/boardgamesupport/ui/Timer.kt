package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Timer(
    viewModel: TimerViewModel = koinViewModel(),
) {
    KeepScreenOn()
    Box(Modifier.fillMaxSize()) {
        Text(
            text = viewModel.remainingTime.toString(),
            modifier = Modifier
                .padding(bottom = 200.dp)
                .align(Alignment.BottomCenter)
                .clickable { viewModel.onCounterClicked() },
            fontSize = 50.sp,
        )
        Text(
            text = viewModel.remainingTime.toString(),
            modifier = Modifier
                .padding(top = 200.dp)
                .align(Alignment.TopCenter)
                .rotate(180f)
                .clickable { viewModel.onCounterClicked() },
            fontSize = 50.sp,
        )
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