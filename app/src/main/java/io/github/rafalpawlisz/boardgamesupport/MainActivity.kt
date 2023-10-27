package io.github.rafalpawlisz.boardgamesupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.rafalpawlisz.boardgamesupport.ui.Die
import io.github.rafalpawlisz.boardgamesupport.ui.Imago
import io.github.rafalpawlisz.boardgamesupport.ui.Menu
import io.github.rafalpawlisz.boardgamesupport.ui.Timer
import io.github.rafalpawlisz.boardgamesupport.ui.WielkiZaklad
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
                        composable("Die") { Die() }
                        composable("Imago") { Imago() }
                        composable("WielkiZaklad") { WielkiZaklad() }
                        composable("Timer") { Timer() }
                    }
                }
            }
        }
    }
}