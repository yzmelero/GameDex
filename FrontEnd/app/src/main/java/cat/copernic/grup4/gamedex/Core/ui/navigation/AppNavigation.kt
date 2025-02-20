package cat.copernic.grup4.gamedex.Core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.UI.Screens.AddCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.Screens.LoginScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.ProfileScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ListGamesScreen
import cat.copernic.grup4.gamedex.videogames.ui.screens.ViewGamesScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.UserListScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.ValidateList
import cat.copernic.grup4.gamedex.Users.UI.Screens.ValidateListScreen
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    //Permet la navegació i portar un historial d'aquesta
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val userRepository = UserRepository() // Inicialitza UserRepository
            val useCases = UseCases(userRepository) // Passa'l a UseCases
            val factory = UserViewModelFactory(useCases) // Crea la Factory
            val viewModel: UserViewModel = viewModel(factory = factory)
            LoginScreen(navController, viewModel) }
        composable("signup") { SignUpScreen(navController) }
        composable("list_category") { ListCategoryScreen(navController) }
        composable("add_category") { AddCategoryScreen(navController) }
        composable("userList") { UserListScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("validate") { ValidateListScreen(navController) }

        composable("listvideogames") { ListGamesScreen(navController) }
        composable("viewGame/{gameId}") { ViewGamesScreen(navController) }
        //Home screen / Category main screen
         composable("category") { ListCategoryScreen(navController)}
    }
}