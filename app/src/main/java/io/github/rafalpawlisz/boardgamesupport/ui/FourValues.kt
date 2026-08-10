package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

/** A quarter of the shorter screen side — about what a phone shows today, but it grows. */
private const val VALUE_FRACTION = 0.25f

/**
 * Rotation only turns the drawing, not the layout, so a tall glyph turned on its side
 * would spill out of its box and off the screen. A square box big enough for the value
 * either way round maps onto itself at every quarter turn, so nothing can escape it.
 */
private const val BOX_FACTOR = 1.4f

/**
 * Shows the same value in all four corners, each turned towards its own edge, so everyone
 * around the table reads it the right way up.
 *
 * The size is handed to [content] rather than fixed, so results scale with the screen
 * instead of being tuned for one device.
 */
@Composable
fun FourValues(
    content: @Composable (size: Dp) -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val size = min(maxWidth, maxHeight) * VALUE_FRACTION
        SingleValue(content, size, Alignment.TopStart, 180f)
        SingleValue(content, size, Alignment.TopEnd, -90f)
        SingleValue(content, size, Alignment.BottomStart, 90f)
        SingleValue(content, size, Alignment.BottomEnd, 0f)
    }
}

/** The corner size expressed as a font size, so text follows the screen as well. */
@Composable
fun Dp.toFontSize(): TextUnit {
    val density = LocalDensity.current
    return with(density) { this@toFontSize.toSp() }
}

@Composable
private fun BoxScope.SingleValue(
    content: @Composable (size: Dp) -> Unit,
    size: Dp,
    alignment: Alignment,
    rotationDegrees: Float,
) {
    Box(
        modifier = Modifier
            .align(alignment)
            .padding(16.dp)
            .size(size * BOX_FACTOR)
            .rotate(rotationDegrees),
        contentAlignment = Alignment.Center,
    ) {
        content(size)
    }
}

@Preview(showBackground = true)
@Composable
private fun FourValuesPreview() {
    FourValues { size ->
        Text(text = "48", fontSize = size.toFontSize())
    }
}
