package io.github.rafalpawlisz.boardgamesupport

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun WielkiZaklad(
    viewModel: WielkiZakladViewModel = koinViewModel(),
    startTone: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues {
            when (viewModel.result) {
                "green" -> Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Green)
                )

                "orange" -> Box(
                    Modifier
                        .size(100.dp)
                        .background(Color(rgb(255, 140, 0)))
                )

                "yellow" -> Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Yellow)
                )

                else -> Text(
                    text = viewModel.result,
                    fontSize = 100.sp,
                )
            }
        }
        PlayButton {
            viewModel.generateResult()
            startTone()
        }
        Timer { startTone() }
    }
}