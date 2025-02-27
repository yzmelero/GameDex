package cat.copernic.grup4.gamedex.Core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.UI.Screens.AddCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Library.UI.Screens.AddGameToLibraryScreen
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
import cat.copernic.grup4.gamedex.Category.UI.Screens.ViewCategoryScreen
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Library.UI.Screens.LibraryScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.AddAdminScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.ResetPasswordScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.ViewValidateUserScreen

import cat.copernic.grup4.gamedex.videogames.ui.screens.AddGamesScreen

@Composable
fun AppNavigation(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    //Permet la navegació i portar un historial d'aquesta
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {LoginScreen(navController, userViewModel) }
        composable("signup") { SignUpScreen(navController, userViewModel) }
        composable("list_category") { ListCategoryScreen(navController, userViewModel) }
        composable("add_category") { AddCategoryScreen(navController, userViewModel) }
        composable("userList/{username}") { UserListScreen(navController, userViewModel) }
        composable("profile/{username}") { ProfileScreen(navController, userViewModel) }
        composable("validate") { ValidateListScreen(navController, userViewModel) }
        composable("listvideogames") { ListGamesScreen(navController, userViewModel) }
        composable("viewGame/{gameId}") { ViewGamesScreen(navController, userViewModel) }
        composable("addGames") { AddGamesScreen(navController, userViewModel) }
        composable("category") { ListCategoryScreen(navController, userViewModel)}
        composable("view_category/{categoryId}") { ViewCategoryScreen(navController, userViewModel) }
        composable("add_admin") { AddAdminScreen(navController, userViewModel) }
        //Home screen / Category main screen
        //composable("category") { ListCategoryScreen(navController)}
        composable("ValidateView/{username}") { ViewValidateUserScreen(navController, userViewModel)}
        composable("resetPassword") {ResetPasswordScreen(navController, userViewModel)}
        composable("addToLibrary/{gameId}") {AddGameToLibraryScreen(navController, userViewModel)}
        composable("libraryScreen") {LibraryScreen(navController,userViewModel)}
    }
}