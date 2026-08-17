package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.rafalpawlisz.boardgamesupport.R
import kotlin.math.max

/** One gap: around the grid, between the rows and between the tiles. */
private val Gap = 16.dp

/** Room for the icon, its gap and a two-line label, with the tile's padding around it. */
private val PreferredTileSide = 180.dp

/**
 * The tile's parts as fractions of its side. Two columns are guaranteed even on a narrow
 * phone, so a tile can end up small; sized in dp the icon and a two-line label would then
 * outgrow the card and be cut off by it.
 */
private const val PADDING_FRACTION = 0.09f
private const val ICON_FRACTION = 0.31f
private const val GAP_FRACTION = 0.07f
private const val LABEL_FRACTION = 0.12f

/** A game in the menu. Adding one is a line in [Menu]'s list; the grid finds room for it. */
private class Game(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val navigate: () -> Unit,
)

@Composable
fun Menu(
    navigateToCatan: () -> Unit,
    navigateToImago: () -> Unit,
    navigateToWielkiZaklad: () -> Unit,
    navigateToFiveSeconds: () -> Unit,
) {
    val games = listOf(
        Game(R.string.menu_catan, R.drawable.deployed_code_24px, navigateToCatan),
        Game(R.string.menu_imago, R.drawable.style_24px, navigateToImago),
        Game(R.string.menu_wielki_zaklad, R.drawable.handshake_24px, navigateToWielkiZaklad),
        Game(R.string.menu_five_seconds, R.drawable.timer_24px, navigateToFiveSeconds),
    )

    BoxWithConstraints(Modifier.fillMaxSize()) {
        // Two columns even on the narrowest phone; a screen with real width gets more
        // columns rather than a handful of stretched tiles.
        val columns = max(2, ((maxWidth - Gap * 2) / PreferredTileSide).toInt())
        // The grid's own padding at both edges, plus a gap between every pair of columns.
        val tileSide = (maxWidth - Gap * (columns + 1)) / columns

        // The tiles keep their square whatever the screen does and the grid scrolls when
        // they stop fitting, so there is no longer a shape of screen the menu has to be
        // talked into. The title scrolls with them: on a short screen the games are what
        // the height is for.
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(Gap),
            horizontalArrangement = Arrangement.spacedBy(Gap),
            verticalArrangement = Arrangement.spacedBy(Gap),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
            items(games) { game ->
                MenuTile(game, tileSide)
            }
        }
    }
}

@Composable
private fun MenuTile(
    game: Game,
    side: Dp,
) {
    Card(
        onClick = game.navigate,
        modifier = Modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(side * PADDING_FRACTION),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(game.icon),
                contentDescription = null,
                modifier = Modifier.size(side * ICON_FRACTION),
            )
            Spacer(Modifier.height(side * GAP_FRACTION))
            Text(
                text = stringResource(game.label),
                fontSize = (side * LABEL_FRACTION).toFontSize(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu(
        navigateToCatan = {},
        navigateToImago = {},
        navigateToWielkiZaklad = {},
        navigateToFiveSeconds = {},
    )
}
