package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.rafalpawlisz.boardgamesupport.R

@Preview(showBackground = true)
@Composable
fun PlayButton(
    onClick: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp),
        ) {
            Text(text = stringResource(R.string.play))
        }
    }
}
