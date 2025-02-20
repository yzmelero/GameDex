package cat.copernic.grup4.gamedex.Core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Users.UI.Screens.LoginScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ListGamesScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ViewGamesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    //Permet la navegació i portar un historial d'aquesta
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("listvideogames") { ListGamesScreen(navController) }
        composable("viewGame/{gameId}") { ViewGamesScreen(navController) }
        //Home screen / Category main screen
        //composable("category") { ListCategoryScreen(navController)}
    }
}