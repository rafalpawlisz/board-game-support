package io.github.rafalpawlisz.boardgamesupport

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Menu(
    navigateToDie: () -> Unit,
    navigateToImago: () -> Unit,
    navigateToWielkiZaklad: () -> Unit,
    navigateToTimer: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Board Game Support",
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            style = TextStyle(fontSize = 60.sp),
        )
        MenuItem(
            "Die",
            R.drawable.deployed_code_24px,
            navigateToDie,
        )
        MenuItem(
            "Imago",
            R.drawable.style_24px,
            navigateToImago,
        )
        MenuItem(
            "Wielki Zakład",
            R.drawable.handshake_24px,
            navigateToWielkiZaklad,
        )
        MenuItem(
            "Timer",
            R.drawable.timer_24px,
            navigateToTimer,
        )
    }
}

@Composable
private fun MenuItem(
    text: String,
    @DrawableRes iconResource: Int = R.drawable.deployed_code_24px,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(4.dp),
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            fontSize = 30.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu(
        navigateToDie = {},
        navigateToImago = {},
        navigateToWielkiZaklad = {},
        navigateToTimer = {},
    )
}