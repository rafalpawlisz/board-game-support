package io.github.rafalpawlisz.boardgamesupport

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun Die(
    viewModel: DieViewModel = koinViewModel(),
    startTone: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues {
            Text(text = viewModel.result, fontSize = 100.sp)
        }
        PlayButton {
            viewModel.generateResult()
            startTone()
        }
    }
}