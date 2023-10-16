package io.github.rafalpawlisz.boardgamesupport

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WielkiZaklad(startTone: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        var result by rememberSaveable { mutableStateOf("") }
        FourValues {
            when (result) {
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
                    text = result,
                    fontSize = 100.sp,
                )
            }
        }
        val firstClick = rememberSaveable { mutableStateOf(true) }
        PlayButton {
            result = if (firstClick.value) {
                firstClick.value = false
                listOf("green", "orange", "yellow").random()
            } else {
                (1..3).random().toString()
            }
            startTone()
        }
        Timer { startTone() }
    }
}