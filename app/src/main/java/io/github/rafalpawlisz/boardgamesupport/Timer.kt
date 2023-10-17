package io.github.rafalpawlisz.boardgamesupport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun Timer(startTone: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        var startTime by rememberSaveable { mutableIntStateOf(30) }
        var remainingTime by rememberSaveable { mutableIntStateOf(startTime) }
        var counting by rememberSaveable { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        Text(
            text = remainingTime.toString(),
            modifier = Modifier
                .padding(bottom = 200.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    if (counting) {
                        counting = false
                    } else {
                        counting = true
                        startTone()
                        coroutineScope.launch {
                            while (remainingTime > 0 && counting) {
                                remainingTime--
                                delay(1.seconds)
                            }
                            counting = false
                            startTone()
                            remainingTime = startTime
                        }
                    }
                },
            fontSize = 50.sp,
        )
        Text(
            text = remainingTime.toString(),
            modifier = Modifier
                .padding(top = 200.dp)
                .align(Alignment.TopCenter)
                .rotate(180f)
                .clickable {
                    if (counting) {
                        counting = false
                    } else {
                        counting = true
                        startTone()
                        coroutineScope.launch {
                            while (remainingTime > 0 && counting) {
                                remainingTime--
                                delay(1.seconds)
                            }
                            counting = false
                            startTone()
                            remainingTime = 30
                        }
                    }
                },
            fontSize = 50.sp,
        )
        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 50.dp),
            onClick = {
                startTime = 30
                remainingTime = 30
            },
        ) {
            Text(text = "30")
        }

        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 50.dp),
            onClick = {
                startTime = 60
                remainingTime = 60
            },
        ) {
            Text(text = "60")
        }
    }
}