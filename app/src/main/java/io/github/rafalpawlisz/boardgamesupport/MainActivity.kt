package io.github.rafalpawlisz.boardgamesupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.rafalpawlisz.boardgamesupport.ui.Die
import io.github.rafalpawlisz.boardgamesupport.ui.FiveSeconds
import io.github.rafalpawlisz.boardgamesupport.ui.Imago
import io.github.rafalpawlisz.boardgamesupport.ui.Menu
import io.github.rafalpawlisz.boardgamesupport.ui.WielkiZaklad
import io.github.rafalpawlisz.boardgamesupport.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navHostController = rememberNavController()
                    NavHost(
                        navController = navHostController,
                        startDestination = Routes.MENU,
                        modifier = Modifier.safeDrawingPadding(),
                    ) {
                        composable(Routes.MENU) {
                            Menu(
                                navigateToDie = { navHostController.navigate(Routes.DIE) },
                                navigateToImago = { navHostController.navigate(Routes.IMAGO) },
                                navigateToWielkiZaklad = { navHostController.navigate(Routes.WIELKI_ZAKLAD) },
                                navigateToFiveSeconds = { navHostController.navigate(Routes.FIVE_SECONDS) },
                            )
                        }
                        composable(Routes.DIE) { Die() }
                        composable(Routes.IMAGO) { Imago() }
                        composable(Routes.WIELKI_ZAKLAD) { WielkiZaklad() }
                        composable(Routes.FIVE_SECONDS) { FiveSeconds() }
                    }
                }
            }
        }
    }
}