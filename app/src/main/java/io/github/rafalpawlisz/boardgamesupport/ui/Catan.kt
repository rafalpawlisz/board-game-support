package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.rafalpawlisz.boardgamesupport.viewmodel.CatanViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The total leads and the two dice sit under it: the table plays off the total, and the
 * dice are there to show how it came about. Both together have to fit the corner box,
 * hence the fractions.
 */
private const val TOTAL_FRACTION = 0.75f
private const val DICE_FRACTION = 0.22f

@Composable
fun Catan(
    viewModel: CatanViewModel = koinViewModel(),
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        FourValues { size ->
            val dice = viewModel.dice
            if (dice.isNotEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Every frame of the animation is a pair of dice, and its total
                    // reads exactly like a real one — the table would act on a number
                    // that is about to change. The dice tumble; the total waits for
                    // them to settle, as it does on a real table.
                    Text(
                        text = viewModel.total.toString(),
                        color = if (viewModel.isRolling) Color.Transparent else Color.Unspecified,
                        fontSize = (size * TOTAL_FRACTION).toFontSize(),
                    )
                    Text(
                        text = dice.joinToString(" + "),
                        fontSize = (size * DICE_FRACTION).toFontSize(),
                    )
                }
            }
        }
        PlayButton {
            viewModel.generateResult()
        }
    }
}
