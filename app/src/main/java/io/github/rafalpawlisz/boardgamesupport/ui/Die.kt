package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rafalpawlisz.boardgamesupport.viewmodel.DieViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Die(
    viewModel: DieViewModel = koinViewModel(),
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues { size ->
            Text(text = viewModel.result, fontSize = size.toFontSize())
        }
        PlayButton {
            viewModel.generateResult()
        }
    }
}