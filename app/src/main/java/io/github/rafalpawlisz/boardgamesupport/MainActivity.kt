package io.github.rafalpawlisz.boardgamesupport

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.rafalpawlisz.boardgamesupport.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navHostController = rememberNavController()
                    NavHost(navController = navHostController, "Menu") {
                        composable("Menu") {
                            Menu(
                                navigateToDie = { navHostController.navigate("Die") },
                                navigateToImago = { navHostController.navigate("Imago") },
                                navigateToWielkiZaklad = { navHostController.navigate("WielkiZaklad") },
                                navigateToTimer = { navHostController.navigate("Timer") },
                            )
                        }
                        composable("Die") { Die { startTone() } }
                        composable("Imago") { Imago { startTone() } }
                        composable("WielkiZaklad") { WielkiZaklad { startTone() } }
                        composable("Timer") { Timer { startTone() } }
                    }
                }
            }
        }
    }

    private fun startTone() = ToneGenerator(
        AudioManager.STREAM_MUSIC,
        ToneGenerator.MAX_VOLUME,
    ).startTone(
        ToneGenerator.TONE_CDMA_ONE_MIN_BEEP,
        200,
    )
}