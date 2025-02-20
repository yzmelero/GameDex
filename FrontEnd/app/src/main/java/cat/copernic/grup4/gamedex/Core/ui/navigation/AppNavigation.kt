package cat.copernic.grup4.gamedex.Core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.UI.Screens.AddCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.LoginScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    //Permet la navegació i portar un historial d'aquesta
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("list_category") { ListCategoryScreen(navController) }
        composable("add_category") { AddCategoryScreen(navController) }

        //Home screen / Category main screen
        //composable("category") { ListCategoryScreen(navController)}
    }
}