package io.github.rafalpawlisz.boardgamesupport.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import io.github.rafalpawlisz.boardgamesupport.R

@Composable
fun Menu(
    navigateToDie: () -> Unit,
    navigateToImago: () -> Unit,
    navigateToWielkiZaklad: () -> Unit,
    navigateToFiveSeconds: () -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        // Square tiles only work while there is height to spare. On a wide screen two
        // rows of squares are taller than the screen, so there the tiles share the
        // leftover height instead.
        val squareTiles = maxHeight > maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp),
            )
            MenuRow(squareTiles) {
                MenuTile(
                    stringResource(R.string.menu_die),
                    R.drawable.deployed_code_24px,
                    squareTiles,
                    navigateToDie,
                )
                MenuTile(
                    stringResource(R.string.menu_imago),
                    R.drawable.style_24px,
                    squareTiles,
                    navigateToImago,
                )
            }
            MenuRow(squareTiles) {
                MenuTile(
                    stringResource(R.string.menu_wielki_zaklad),
                    R.drawable.handshake_24px,
                    squareTiles,
                    navigateToWielkiZaklad,
                )
                MenuTile(
                    stringResource(R.string.menu_five_seconds),
                    R.drawable.timer_24px,
                    squareTiles,
                    navigateToFiveSeconds,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.MenuRow(
    squareTiles: Boolean,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = if (squareTiles) {
            Modifier.fillMaxWidth()
        } else {
            Modifier
                .fillMaxWidth()
                .weight(1f)
        },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}

@Composable
private fun RowScope.MenuTile(
    text: String,
    @DrawableRes iconResource: Int,
    squareTiles: Boolean,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .then(if (squareTiles) Modifier.aspectRatio(1f) else Modifier.fillMaxHeight()),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    ) {
        // A short tile has no room to stack the icon above the label, but plenty of
        // width beside it.
        if (squareTiles) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TileIcon(iconResource)
                Spacer(Modifier.height(12.dp))
                TileLabel(text)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TileIcon(iconResource)
                Spacer(Modifier.width(12.dp))
                TileLabel(text)
            }
        }
    }
}

@Composable
private fun TileIcon(
    @DrawableRes iconResource: Int,
) {
    Icon(
        painter = painterResource(iconResource),
        contentDescription = null,
        modifier = Modifier.size(56.dp),
    )
}

@Composable
private fun TileLabel(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu(
        navigateToDie = {},
        navigateToImago = {},
        navigateToWielkiZaklad = {},
        navigateToFiveSeconds = {},
    )
}
