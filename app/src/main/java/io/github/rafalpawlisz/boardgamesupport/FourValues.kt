package io.github.rafalpawlisz.boardgamesupport

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FourValues(
    composable: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SingleValue(composable, Alignment.TopStart, 135f)
        SingleValue(composable, Alignment.TopEnd, -135f)
        SingleValue(composable, Alignment.BottomStart, 45f)
        SingleValue(composable, Alignment.BottomEnd, -45f)
    }
}

@Composable
private fun BoxScope.SingleValue(
    composable: @Composable () -> Unit,
    alignment: Alignment,
    rotationDegrees: Float,
) {
    Box(
        modifier = Modifier
            .align(alignment)
            .padding(16.dp)
            .rotate(rotationDegrees),
    ) {
        composable()
    }
}

@Preview(showBackground = true)
@Composable
private fun FourValuesPreview() {
    FourValues {
        Text(text = "48", fontSize = 100.sp)
    }
}