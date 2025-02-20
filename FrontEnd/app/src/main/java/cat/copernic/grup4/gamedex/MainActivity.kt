package cat.copernic.grup4.gamedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.ui.navigation.AppNavigation
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.AddGamesScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ListGamesScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ViewGamesScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val fakeNavController = rememberNavController()
            ViewGamesScreen(navController = fakeNavController)
        }
    }
}
