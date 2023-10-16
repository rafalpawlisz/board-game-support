package io.github.rafalpawlisz.boardgamesupport

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Imago(startTone: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        val result = rememberSaveable { mutableStateOf("") }
        FourValues {
            Text(text = result.value, fontSize = 100.sp)
        }
        PlayButton {
            result.value = (1..8).random().toString()
            startTone()
        }
    }
}