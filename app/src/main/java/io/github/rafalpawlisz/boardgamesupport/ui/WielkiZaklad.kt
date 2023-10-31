package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.rafalpawlisz.boardgamesupport.viewmodel.WielkiZakladViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WielkiZaklad(
    viewModel: WielkiZakladViewModel = koinViewModel(),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues {
            when (viewModel.result) {
                is WielkiZakladViewModel.ColorResult -> Box(
                    Modifier
                        .size(100.dp)
                        .background((viewModel.result as WielkiZakladViewModel.ColorResult).color)
                )
                is WielkiZakladViewModel.TextResult -> Text(
                    text = (viewModel.result as WielkiZakladViewModel.TextResult).text,
                    fontSize = 100.sp,
                )
            }
        }
        PlayButton {
            viewModel.generateResult()
        }
        Timer()
    }
}